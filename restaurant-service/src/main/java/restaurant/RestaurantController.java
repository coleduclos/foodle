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
    public String addRestaurant(@RequestBody Restaurant restaurant) {
        return "Adding restaurant: " + restaurant.getName() + ", " + restaurant.getUuid();
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
			System.out.println("Found! Restaurant name: " + temp.getName() + " Restaurant ID: " + temp.getUuid().toString());

		return result;
	}

}