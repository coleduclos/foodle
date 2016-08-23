package recommend;

import java.util.UUID;

import com.datastax.driver.mapping.annotations.Accessor;
import com.datastax.driver.mapping.annotations.Query;
import com.datastax.driver.mapping.Result;
import com.datastax.driver.mapping.annotations.Param;  

@Accessor
public interface RatingAccessor {

	@Query("SELECT * FROM rating WHERE restaurant_id=:restaurant_id")
	Result<Rating> getAllByRestaurantId(@Param("restaurant_id") UUID restaurant_id);

	@Query("SELECT * FROM rating WHERE user_id=:user_id")
	Result<Rating> getAllByUserId(@Param("user_id") UUID user_id);

	@Query("SELECT * FROM rating WHERE user_id=:user_id AND rating_value=:rating_value ALLOW FILTERING")
	Result<Rating> getAllByUserIdAndRatingValue(@Param("user_id") UUID user_id, @Param("rating_value") int rating_value);

	@Query("SELECT * FROM rating WHERE restaurant_id=:restaurant_id AND rating_value=:rating_value ALLOW FILTERING")
	Result<Rating> getAllByRestaurantIdAndRatingValue(@Param("restaurant_id") UUID restaurant_id, @Param("rating_value") int rating_value);

}