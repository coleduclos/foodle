package recommend;

import com.datastax.driver.core.utils.UUIDs; 
import java.util.UUID;
import com.datastax.driver.mapping.annotations.Table;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Column;

@Table ( name = "user")
public class User {

	@PartitionKey
	@Column ( name = "user_id" )
	private UUID uuid;

	@Column ( name = "user_email")
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

	public String toString ()
	{
		return "User ID: " + this.uuid + 
			" User Email: " + this.email;
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