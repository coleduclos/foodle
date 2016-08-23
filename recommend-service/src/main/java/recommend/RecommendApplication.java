package recommend;

import java.util.UUID;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import com.datastax.driver.mapping.Mapper;

@EnableDiscoveryClient
@SpringBootApplication
public class RecommendApplication {

	public static void main(String[] args) {

		SpringApplication.run(RecommendApplication.class, args);

		/*UUID rating_id = UUID.fromString("1ed1f1f0-67f6-11e6-896f-5facf054b1a9");

		CassandraClient client = new CassandraClient(); 
		client.connect();
		
		Mapper<Rating> ratingMapper = client.getMappingManager().mapper(Rating.class);
		Rating rating = ratingMapper.get(rating_id);
		if ( rating != null )
			System.out.println("Found Rating: " + rating.toString());
		else
			System.out.println( "ERROR: Could not find rating: " + rating.getUuid());

		Mapper<User> userMapper = client.getMappingManager().mapper(User.class);
		User user = userMapper.get(rating.getUser_id()); 
		if ( user != null )
			System.out.println("Found User: " + user.toString());
		else
			System.out.println("ERROR: Could not find associated user: " + rating.getUser_id());

		Mapper<Restaurant> restaurantMapper = client.getMappingManager().mapper(Restaurant.class);
		Restaurant restaurant = restaurantMapper.get(rating.getRestaurant_id());
		if ( restaurant != null )
			System.out.println("Found Restaurant: " + restaurant.toString());
		else
			System.out.println("ERROR: Cloud not find associated restaurant: " + rating.getRestaurant_id());*/


	}

}