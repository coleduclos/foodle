	package restaurant;

import com.datastax.driver.core.utils.UUIDs; 
import java.util.UUID;
import com.datastax.driver.mapping.annotations.Table;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.ClusteringColumn;
import com.datastax.driver.mapping.annotations.Column;


@Table ( name = "restaurant")
public class Restaurant {

	@PartitionKey
	@Column ( name = "restaurant_name" )
	private String name;

	@ClusteringColumn
	@Column ( name = "restaurant_id" )
	private UUID uuid;

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