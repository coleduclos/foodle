package restaurant;

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
@RequestMapping("/restaurant")
public class RestaurantController {

	@RequestMapping(value="/add", method=RequestMethod.POST)
	@ResponseBody
	public String addRestaurant(@RequestBody Restaurant restaurant) {

		CassandraClient client = new CassandraClient(); 
		client.connect();

		Mapper mapper = client.getMappingManager().mapper(Restaurant.class);
		mapper.save(restaurant);

		client.close();

		return "SUCCESS! Added Restaurant: " + restaurant.toString();
	}

	@RequestMapping(value="/{restaurant_name}", method = RequestMethod.GET)
	@ResponseBody
	public Result<Restaurant> findRestaurant(@PathVariable String restaurant_name)
	{
		CassandraClient client = new CassandraClient(); 
		client.connect();
		System.out.println("Finding Restaurant: " + restaurant_name);
		
		RestaurantAccessor restaurantAccessor = client.getMappingManager().createAccessor(RestaurantAccessor.class);
		Result<Restaurant> result = restaurantAccessor.getByName(restaurant_name);

		client.close();

		Restaurant temp = result.one();
		if (temp != null )
			System.out.println("FOUND! Restaurant name: " + temp.toString());

		return result;
	}

}