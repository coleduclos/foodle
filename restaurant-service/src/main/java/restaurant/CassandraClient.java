package restaurant;

import com.datastax.driver.core.Cluster;  
import com.datastax.driver.core.Host;  
import com.datastax.driver.core.Metadata;  
import com.datastax.driver.core.Session;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.BoundStatement; 
import com.datastax.driver.mapping.MappingManager;
 
public class CassandraClient  
{   
  private Cluster cluster;  
  private Session session;
  private String keyspace = "dev";
  private String cassIp="localhost";
  private int cassPort=9042; 
  private MappingManager mappingManager; 

  public void connect()  
  { 
    System.out.println("Connecting to " + this.cassIp + ":" + this.cassPort); 

    this.cluster = Cluster.builder().addContactPoint(this.cassIp).withPort(this.cassPort).build();

    Metadata metadata = cluster.getMetadata();

    for ( Host host : metadata.getAllHosts() ) 
    {
      System.out.printf("Datacenter: %s; Host: %s; Rack: %s\n",
        host.getDatacenter(), host.getAddress(), host.getRack());
    } 

    session = cluster.connect(this.keyspace);
    System.out.printf("Connected to cluster: %s\n", metadata.getClusterName());
    mappingManager = new MappingManager(session);
    System.out.printf("Created MappingManager");

  }

  public Session getSession()  
  {  
    return this.session;  
  }

  public MappingManager getMappingManager()
  {
    return this.mappingManager;
  }

  public void close()  
  {  
    cluster.close();  
  }  
} 