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