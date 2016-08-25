package recommend;

import java.util.UUID;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

import com.datastax.driver.mapping.annotations.Table;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.Result;
import com.datastax.driver.mapping.Mapper; 

@Table ( name = "similar_users")
public class SimilarUsers {

	@PartitionKey
	private UUID user_id;

	private Map<UUID, Double> similar_users;

	public SimilarUsers (){
		this.similar_users = new HashMap<UUID, Double>();
	}

	public SimilarUsers ( UUID user_id ){
		this.user_id = user_id;
		this.similar_users = new HashMap<UUID, Double>();
	}

	public SimilarUsers ( UUID user_id, HashMap<UUID, Double> similar_users ){
		this.user_id = user_id;
		this.similar_users = new HashMap<UUID, Double>(similar_users);
	}
	 
	public void update( CassandraClient client ){

		RatingAccessor ratingAccessor = client.getMappingManager().createAccessor(RatingAccessor.class);

		List<Rating> user_ratings_list= ratingAccessor.getAllByUserId( this.user_id ).all();
		List<Rating> other_user_ratings_list = new ArrayList<Rating>();
		HashMap<UUID, Integer> user_ratings_map = new HashMap<UUID, Integer>();

		for (Rating r : user_ratings_list)
		{
			user_ratings_map.put(r.getRestaurant_id(), r.getRating_value());
			other_user_ratings_list.addAll(ratingAccessor.getAllByRestaurantId(r.getRestaurant_id()).all());
		}

		int user_ratings_size = user_ratings_map.size();

		// for each user who rated the restaurant calculate the similarity index
		Iterator<Rating> other_user_rating_iterator = other_user_ratings_list.iterator();
		while(other_user_rating_iterator.hasNext())
		{
			Rating other_user_rating = other_user_rating_iterator.next();
			if(!this.user_id.equals(other_user_rating.getUser_id()))
			{
				// Initialize the similarity index numerator and demoninator 
				int similarity_idx_numerator = 0;
				int similarity_idx_demoninator = user_ratings_size;

				Result<Rating> compare_user_ratings = ratingAccessor.getAllByUserId( other_user_rating.getUser_id() );
				while(!compare_user_ratings.isExhausted())
				{
					Rating compare_user_rating = compare_user_ratings.one();

					Integer user_rating = user_ratings_map.get(compare_user_rating.getRestaurant_id());
					// Check if both have rated the same restaurants previously
					if (user_rating != null)
					{
						// Check if user has similar tastes
						if(user_rating == compare_user_rating.getRating_value())
						{
							similarity_idx_numerator ++;						
						}
						else
						{
							similarity_idx_numerator --;
						}
					}
					// If the user has not rated this restaurant, add it to the denominator 
					else
						similarity_idx_demoninator ++;

				}

				double similarity_idx = (double) similarity_idx_numerator / similarity_idx_demoninator;
				this.similar_users.put(other_user_rating.getUser_id(), similarity_idx);
			}
		}
		Mapper mapper = client.getMappingManager().mapper(SimilarUsers.class);
		mapper.save(this);
		print();
	}

	public void print()
	{
		System.out.println("Below are the calculated Similarity Indices for User: " + this.user_id);
		for (UUID id : this.similar_users.keySet())
		{
			System.out.println("User: " + id + " Similarity Index: " + this.similar_users.get(id));
		}
	}

	public UUID getUser_id(){
		return this.user_id;
	}

	public void setUser_id( UUID user_id ){
		this.user_id = user_id;
	}

	public Map<UUID, Double> getSimilar_users(){
		return this.similar_users;
	}

	public void setSimilar_users( HashMap<UUID, Double> similar_users ){
		this.similar_users = similar_users;
	}
}