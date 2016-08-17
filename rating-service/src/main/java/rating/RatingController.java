package rating;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.Result;

@RestController
@RequestMapping("/rating")
public class RatingController {

	@RequestMapping(value="/add", method=RequestMethod.POST)
	@ResponseBody
	public String addRestaurant(@RequestBody Rating rating) {

		CassandraClient client = new CassandraClient(); 
		client.connect();
		Mapper mapper = client.getMappingManager().mapper(Rating.class);
		mapper.save(rating);

		client.close();

		return "SUCCESS! Added Rating: " + rating.toString();
	}

	@RequestMapping(value="/get-all-by-user/{user_id}", method = RequestMethod.GET)
	@ResponseBody
	public Result<Rating> getAllRatingsByUserId(@PathVariable UUID user_id)
	{
		CassandraClient client = new CassandraClient(); 
		client.connect();
		System.out.println("Finding Ratings for User ID: " + user_id);
		
		RatingAccessor ratingAccessor = client.getMappingManager().createAccessor(RatingAccessor.class);
		Result<Rating> result = ratingAccessor.getAllByUserId( user_id );

		client.close();

		Rating temp = result.one();
		if (temp != null )
			System.out.println( "FOUND! " + temp.toString());

		return result;
	}

	@RequestMapping(value="/get-all-by-restaurant/{restaurant_id}", method = RequestMethod.GET)
	@ResponseBody
	public Result<Rating> getAllRatingsByRestaurantId(@PathVariable UUID restaurant_id)
	{
		CassandraClient client = new CassandraClient(); 
		client.connect();
		System.out.println("Finding Ratings for User ID: " + restaurant_id);
		
		RatingAccessor ratingAccessor = client.getMappingManager().createAccessor(RatingAccessor.class);
		Result<Rating> result = ratingAccessor.getAllByRestaurantId( restaurant_id );

		client.close();

		Rating temp = result.one();
		if (temp != null )
			System.out.println( "FOUND! " + temp.toString());

		return result;
	}

}