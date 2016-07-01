import com.datastax.driver.core.Cluster;  
import com.datastax.driver.core.Host;  
import com.datastax.driver.core.Metadata;  
import com.datastax.driver.core.Session;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.BoundStatement;  
 
public class CassandraClient  
{   
   private Cluster cluster;  
   private Session session;  
 
  public void connect(final String node, final int port, final String keyspace)  
  { 
    System.out.println("Connecting to " + node + ":" + port); 
    this.cluster = Cluster.builder().addContactPoint(node).withPort(port).build(); 
    Metadata metadata = cluster.getMetadata();
    System.out.printf("Connected to cluster: %s\n", metadata.getClusterName());
    for ( Host host : metadata.getAllHosts() ) 
    {
        System.out.printf("Datacenter: %s; Host: %s; Rack: %s\n",
          host.getDatacenter(), host.getAddress(), host.getRack());
    } 
    session = cluster.connect(keyspace);  
  } 

  public void addUser(User user) 
  { 
    PreparedStatement statement = getSession().prepare(
      "INSERT INTO user " +
      "(user_id, user_email) " +
      "VALUES (?, ?);");
    BoundStatement boundStatement = new BoundStatement(statement);
    getSession().execute(boundStatement.bind( user.getUuid(), user.getEmail()));
  }

  public void addRestaurant(Restaurant restaurant) 
   { 
      PreparedStatement statement = getSession().prepare(
        "INSERT INTO restaurant " +
        "(restaurant_id, restaurant_name) " +
        "VALUES (?, ?);");
      BoundStatement boundStatement = new BoundStatement(statement);
      getSession().execute(boundStatement.bind( restaurant.getUuid(), restaurant.getName()));
   }  
 

   public Session getSession()  
   {  
      return this.session;  
   }  
 
   public void close()  
   {  
      cluster.close();  
   }  
} 