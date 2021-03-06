package restaurant;

import java.util.UUID;
import com.datastax.driver.core.utils.UUIDs; 
import com.datastax.driver.mapping.annotations.Table;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Column;


@Table ( name = "restaurant")
public class Restaurant {

	@PartitionKey
	@Column ( name = "restaurant_id" )
	private UUID uuid;

	@Column ( name = "restaurant_name" )
	private String name;

	public Restaurant ()
	{
		this.name = "";
		this.uuid = UUIDs.timeBased();
	}

	public Restaurant (String name)
	{
		this.name = name;
		this.uuid = UUIDs.timeBased();
	}

	public String toString ()
	{
		return "Restaurant ID: " + this.uuid + 
			" Restaurant Name: " + this.name;
	}
	public UUID getUuid ()
	{
		return this.uuid;
	}
	public void setUuid ( UUID uuid )
	{
		this.uuid = uuid;
	}

	public String getName ()
	{
		return this.name;
	}
	public void setName( String name )
	{
		this.name = name;
	}

}