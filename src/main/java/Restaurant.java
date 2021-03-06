import com.datastax.driver.core.utils.UUIDs; 
import java.util.UUID;
public class Restaurant {

	private UUID uuid;
	private String name;

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