package restaurant;

import com.datastax.driver.mapping.annotations.Accessor;
import com.datastax.driver.mapping.annotations.Query;
import com.datastax.driver.mapping.Result;
import com.datastax.driver.mapping.annotations.Param;  

@Accessor
public interface RestaurantAccessor {

	@Query("SELECT * FROM restaurant WHERE restaurant_name=:restaurant_name")
    Result<Restaurant> getByName(@Param("restaurant_name") String restaurant_name);

}