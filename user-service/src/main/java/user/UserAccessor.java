package user;

import com.datastax.driver.mapping.annotations.Accessor;
import com.datastax.driver.mapping.annotations.Query;
import com.datastax.driver.mapping.Result;
import com.datastax.driver.mapping.annotations.Param;  

@Accessor
public interface UserAccessor {

	@Query("SELECT * FROM user WHERE user_email=:user_email")
    Result<User> getByEmail( @Param("user_email") String user_email );

}