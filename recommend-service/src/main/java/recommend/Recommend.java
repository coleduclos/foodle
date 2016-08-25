package recommend;

import com.datastax.driver.core.utils.UUIDs;
import com.datastax.driver.mapping.Result; 
import java.util.UUID;
import java.util.Iterator;
import java.util.List;
import java.util.HashMap;

public class Recommend {

	private UUID user_id;

	private UUID restaurant_id;

	private int recommend_value;

	public Recommend()
	{
		this.recommend_value = -1;
	}

	public Recommend (UUID user_id, UUID restaurant_id)
	{
		this.user_id = user_id;
		this.restaurant_id = restaurant_id;
		this.recommend_value = -1;
	}

	public Recommend (String user_id, String restaurant_id)
	{
		this.user_id = UUID.fromString(user_id);
		this.restaurant_id = UUID.fromString(restaurant_id);
		this.recommend_value = -1;
	}

	HashMap<UUID, Double> calculateSimilarityIndicesByRatingValue(CassandraClient client, int rating_value)
	{

		RatingAccessor ratingAccessor = client.getMappingManager().createAccessor(RatingAccessor.class);
		System.out.println("Finding users who have rated restaurant:" + this.restaurant_id + 
			" with rating value " + rating_value);
		Result<Rating> rating_results = ratingAccessor.getAllByRestaurantIdAndRatingValue( this.restaurant_id, rating_value );

		List<Rating> user_ratings_list= ratingAccessor.getAllByUserId( this.user_id ).all();
		HashMap<UUID, Integer> user_ratings_map = new HashMap<UUID, Integer>();
		HashMap<UUID, Double> user_similarity_indices = new HashMap<UUID, Double>();

		for (Rating r : user_ratings_list)
		{
			user_ratings_map.put(r.getRestaurant_id(), r.getRating_value());
		}

		int user_ratings_size = user_ratings_map.size();

		// for each user who rated the restaurant (with the specified value) calculate the similarity index
		Iterator<Rating> ratingIterator = rating_results.iterator();
		while(ratingIterator.hasNext())
		{
			Rating rating = ratingIterator.next();
			if(!user_id.equals(rating.getUser_id()))
			{
				// Initialize the similarity index numerator and demoninator 
				int similarity_idx_numerator = 0;
				int similarity_idx_demoninator = user_ratings_size;

				Result<Rating> compare_ratings = ratingAccessor.getAllByUserId( rating.getUser_id() );
				while(!compare_ratings.isExhausted())
				{
					Rating compare_rating = compare_ratings.one();

					Integer user_rating = user_ratings_map.get(compare_rating.getRestaurant_id());
					// Check if both have rated the same restaurants previously
					if (user_rating != null)
					{
						// Check if user has similar tastes
						if(user_rating == compare_rating.getRating_value())
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
				System.out.println("Calculating Similarity Index... " + 
					similarity_idx_numerator + " / " + similarity_idx_demoninator + " = " + similarity_idx);
				user_similarity_indices.put(rating.getUser_id(), similarity_idx);
			}
		}
		System.out.println("Below are the calculated Similarity Indices for User: " + this.user_id);
		for (UUID id : user_similarity_indices.keySet())
		{
			System.out.println("User: " + id + " Similarity Index: " + user_similarity_indices.get(id));
		}

		return user_similarity_indices;
	}

	public String toString()
	{
		return "Reccomendation for User ID: " + this.user_id + 
			" Restaurant ID: " + this.restaurant_id +
			" Recommend Value: " + this.recommend_value;
	}

	public UUID getUser_id ()
	{
		return this.user_id;
	}
	public void setUser_id ( String user_id )
	{
		this.user_id = UUID.fromString(user_id);
	}

	public UUID getRestaurant_id ()
	{
		return this.restaurant_id;
	}
	public void setRestaurant_id ( String restaurant_id )
	{
		this.restaurant_id = UUID.fromString(restaurant_id);
	}

	public int getRecommend_value ()
	{
		return this.recommend_value;
	}
	public void setRecommend_value ( int recommend_value )
	{
		this.recommend_value = recommend_value;
	}

}