package kopo.poly;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableEurekaClient
@EnableJpaRepositories
@SpringBootApplication
public class NlpServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(NlpServiceApplication.class, args);
    }

}
