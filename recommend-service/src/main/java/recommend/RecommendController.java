package recommend;

import java.util.UUID;
import java.util.Iterator;
import java.util.List;
import java.util.HashMap;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import com.datastax.driver.mapping.Result;

@RestController
public class RecommendController {

	@RequestMapping(value="/recommendation", method=RequestMethod.GET)
	@ResponseBody
	public double getRecommendation(@RequestBody Recommend recommend) {

		UUID user_id = recommend.getUser_id();
		UUID restaurant_id = recommend.getRestaurant_id();

		int like_value = 5;
		int dislike_value = 0;

		CassandraClient client = new CassandraClient(); 
		client.connect();

		HashMap<UUID, Double> liked_similarity_indices = 
			recommend.calculateSimilarityIndicesByRatingValue(client, like_value);
		HashMap<UUID, Double> disliked_similarity_indices = 
			recommend.calculateSimilarityIndicesByRatingValue(client, dislike_value);

		double probability_numerator = 0;
		double probability_demoninator = liked_similarity_indices.size() + disliked_similarity_indices.size();
		for(UUID id : liked_similarity_indices.keySet())
		{
			probability_numerator = probability_numerator + liked_similarity_indices.get(id);
		}
		for(UUID id : disliked_similarity_indices.keySet())
		{
			probability_numerator = probability_numerator - disliked_similarity_indices.get(id);
		}

		System.out.println("The probabliity User: " + user_id + " likes Restaurant: " + restaurant_id + 
			" is: " + probability_numerator / probability_demoninator);

		client.close();

		return probability_numerator / probability_demoninator;
	}

	@RequestMapping(value="/test", method=RequestMethod.GET)
	@ResponseBody
	public void testFunction(@RequestBody Recommend recommend) {

		UUID user_id = recommend.getUser_id();
		UUID restaurant_id = recommend.getRestaurant_id();

		CassandraClient client = new CassandraClient(); 
		client.connect();
		SimilarUsers similars = new SimilarUsers(user_id);
		similars.update(client);

		RestaurantSuggestions suggestions = new RestaurantSuggestions(user_id);
		suggestions.update(client);

		client.close();
	}

}