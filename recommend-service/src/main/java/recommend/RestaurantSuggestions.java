package recommend;

import java.util.UUID;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

import com.datastax.driver.mapping.annotations.Table;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.Result;
import com.datastax.driver.mapping.Mapper; 

@Table ( name = "restaurant_suggestions")
public class RestaurantSuggestions {

	@PartitionKey
	private UUID user_id;

	private Map<UUID, Double> restaurant_suggestions;

	public RestaurantSuggestions()
	{
		this.restaurant_suggestions = new HashMap<UUID, Double>();
	}

	public RestaurantSuggestions(UUID user_id)
	{
		this.user_id = user_id;
		this.restaurant_suggestions = new HashMap<UUID, Double>();
	}

	public RestaurantSuggestions(UUID user_id, HashMap<UUID, Double> restaurant_suggestions)
	{
		this.user_id = user_id;
		this.restaurant_suggestions = new HashMap<UUID, Double> (restaurant_suggestions);
	}

	public void update(CassandraClient client)
	{
		
		int like_value = 5;
		int dislike_value = 0;

		Mapper<SimilarUsers> similar_users_mapper = client.getMappingManager().mapper(SimilarUsers.class);
		SimilarUsers similarUsers = similar_users_mapper.get(user_id);

		RestaurantAccessor restaurantAccessor = client.getMappingManager().createAccessor(RestaurantAccessor.class);
		Result<Restaurant> all_restaurants = restaurantAccessor.getAll();

		RatingAccessor ratingAccessor = client.getMappingManager().createAccessor(RatingAccessor.class);

		while(!all_restaurants.isExhausted())
		{
			Restaurant restaurant = all_restaurants.one();

			List<Rating> like_ratings = 
				ratingAccessor.getAllByRestaurantIdAndRatingValue(restaurant.getUuid(), like_value).all();
			List<Rating> dislike_ratings = 
				ratingAccessor.getAllByRestaurantIdAndRatingValue(restaurant.getUuid(), dislike_value).all();

			double probability_numerator = 0;
			double probability_demoninator = like_ratings.size() + dislike_ratings.size();

			for ( Rating r : like_ratings )
			{
				Double similarity_idx = similarUsers.getSimilar_users().get(r.getUser_id());
				if ( similarity_idx != null ) 
					probability_numerator += similarity_idx;
			}
			for ( Rating r : dislike_ratings )
			{
				Double similarity_idx = similarUsers.getSimilar_users().get(r.getUser_id());
				if ( similarity_idx != null ) 
					probability_numerator -= similarity_idx;
			}
			if(probability_demoninator != 0)
				this.restaurant_suggestions.put( restaurant.getUuid(), 
						(probability_numerator / probability_demoninator) );
		}

		Mapper restaurant_suggestions_mapper = client.getMappingManager().mapper(RestaurantSuggestions.class);
		restaurant_suggestions_mapper.save(this);

		print();
	}

	public void print()
	{
		System.out.println("Below are the calculated Restaurant Suggestions for User: " + this.user_id);
		for (UUID id : this.restaurant_suggestions.keySet())
		{
			System.out.println("Restaurant: " + id + " Suggestion Weight: " + this.restaurant_suggestions.get(id));
		}
	}

	public UUID getUser_id(){
		return this.user_id;
	}

	public void setUser_id( UUID user_id ){
		this.user_id = user_id;
	}

	public Map<UUID, Double> getRestaurant_suggestions(){
		return this.restaurant_suggestions;
	}

	public void setRestaurant_suggestions( HashMap<UUID, Double> restaurant_suggestions ){
		this.restaurant_suggestions = restaurant_suggestions;
	}
}