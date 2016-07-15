package user;

import com.datastax.driver.core.utils.UUIDs; 
import java.util.UUID;

public class User {

	private UUID uuid;
	private String email;

	public User ()
	{
		this.email = "";
		this.uuid = UUIDs.timeBased();
	}

	public User (String email)
	{
		this.email = email;
		this.uuid = UUIDs.timeBased();
	}

	public UUID getUuid ()
	{
		return this.uuid;
	}
	public void setUuid ( UUID uuid )
	{
		this.uuid = uuid;
	}

	public String getEmail ()
	{
		return this.email;
	}
	public void setEmail( String email )
	{
		this.email = email;
	}

}