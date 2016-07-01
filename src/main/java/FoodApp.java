public class FoodApp
{
	public static boolean user_test(CassandraClient client)
	{
        User peytonUser = new User ("peyton.manning@gmail.com");
        User tomUser = new User ("tom.brady@gmail.com");
        User russellUser = new User ("russell.wilson@gmail.com");
        User aaronUser = new User ("aaron.rogers@gmail.com");
		client.addUser(peytonUser);
		client.addUser(tomUser);
		client.addUser(russellUser);
		client.addUser(aaronUser);

        return true;
	}
	public static boolean restaurant_test(CassandraClient client)
	{
        Restaurant bjs = new Restaurant ("BJ's");
        Restaurant chilis = new Restaurant ("Chili's");
        Restaurant dark_horse = new Restaurant ("Dark Horse");
        Restaurant pasta_jays = new Restaurant ("Pasta Jay's");
        client.addRestaurant(bjs);
        client.addRestaurant(chilis);
        client.addRestaurant(dark_horse);
        client.addRestaurant(pasta_jays);

        return true;
	}
	public static void main(String[] args) 
	{
		System.out.println("Beginning FoodApp");
		String keyspace = "dev";
		String cassIp="127.0.0.1";
		int cassPort=9042;

		
		// Connect to the cluster and keyspace
		CassandraClient client = new CassandraClient(); 
		client.connect(cassIp, cassPort, keyspace);
        user_test(client);
        restaurant_test(client);
		client.close();

    }
}