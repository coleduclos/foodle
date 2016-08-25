package recommend;

import java.util.UUID;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import com.datastax.driver.mapping.Mapper;

@EnableDiscoveryClient
@SpringBootApplication
public class RecommendApplication {

	public static void main(String[] args) {

		SpringApplication.run(RecommendApplication.class, args);

	}

}