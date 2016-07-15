package user; 

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.BoundStatement;  

@RestController
@RequestMapping("/user")
public class UserController {

    @RequestMapping(value="/add", method=RequestMethod.POST)
    @ResponseBody
    public String addUser(@RequestBody User user) {

		CassandraClient client = new CassandraClient(); 
		client.connect();

		PreparedStatement statement = client.getSession().prepare(
			"INSERT INTO user " +
			"(user_id, user_email) " +
			"VALUES (?, ?);"
		);

		BoundStatement boundStatement = new BoundStatement(statement);
		client.getSession().execute(boundStatement.bind( user.getUuid(), user.getEmail()));
		client.close();

        return "Added user: " + user.getEmail() + ", " + user.getUuid();
    }

}