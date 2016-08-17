package rating;

import com.datastax.driver.core.utils.UUIDs; 
import java.util.UUID;
import com.datastax.driver.mapping.annotations.Table;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.ClusteringColumn;
import com.datastax.driver.mapping.annotations.Column;


@Table ( name = "rating")
public class Rating {

	@PartitionKey
	@Column ( name = "rating_id" )
	private UUID uuid;

	private UUID user_id;

	private UUID restaurant_id;

	private int rating_value;

	public Rating ()
	{
		this.uuid = UUIDs.timeBased();
	}

	public Rating (UUID user_id, UUID restaurant_id, int rating_value)
	{
		this.uuid = UUIDs.timeBased();
		this.user_id = user_id;
		this.restaurant_id = restaurant_id;
		this.rating_value = rating_value;
	}

	public Rating (String user_id, String restaurant_id, int rating_value)
	{
		this.uuid = UUIDs.timeBased();
		this.user_id = UUID.fromString(user_id);
		this.restaurant_id = UUID.fromString(restaurant_id);
		this.rating_value = rating_value;
	}

	public String toString()
	{
		return "Rating ID: " + this.uuid + 
			" User ID: " + this.user_id + 
			" Restaurant ID: " + this.restaurant_id +
			" Rating Value: " + this.rating_value;
	}

	public UUID getUuid ()
	{
		return this.uuid;
	}
	public void setUuid ( UUID uuid )
	{
		this.uuid = uuid;
	}

	public UUID getUser_id ()
	{
		return this.user_id;
	}
	public void setUser_id ( String user_id )
	{
		this.user_id = UUID.fromString(user_id);
	}

	public UUID getRestaurant_id ()
	{
		return this.restaurant_id;
	}
	public void setRestaurant_id ( String restaurant_id )
	{
		this.restaurant_id = UUID.fromString(restaurant_id);
	}

	public int getRating_value ()
	{
		return this.rating_value;
	}
	public void setRating_value ( int rating_value )
	{
		this.rating_value = rating_value;
	}

}