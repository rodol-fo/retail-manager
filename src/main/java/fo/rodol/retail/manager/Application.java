package fo.rodol.retail.manager;

import fo.rodol.retail.manager.domain.Shop;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {

        return new RestTemplate();
    }

    @Bean
    public Map<String, Shop> shopMap() {

        return new HashMap<>();
    }

}
