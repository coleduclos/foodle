package user; 

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.Result;

@RestController
@RequestMapping("/user")
public class UserController {

	@RequestMapping(value="/add", method=RequestMethod.POST)
	@ResponseBody
	public String addUser(@RequestBody User user) {

		CassandraClient client = new CassandraClient(); 
		client.connect();

		Mapper mapper = client.getMappingManager().mapper(User.class);
		mapper.save(user);

		client.close();

		return "SUCCESS! Added Restaurant: " + user.toString();
	}

}