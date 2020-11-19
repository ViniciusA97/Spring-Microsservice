package learning.microservice.note;

import learning.microservice.core.property.JwtConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan({"learning.microservice.core.model.note"})
@EnableJpaRepositories({"learning.microservice.core.repository.note"})
@EnableConfigurationProperties(value= JwtConfiguration.class)
@ComponentScan("learning.microservice")
@EnableFeignClients
@EnableCircuitBreaker
@EnableHystrix
public class NoteApplication {

    public static void main(String[] args) {
        SpringApplication.run(NoteApplication.class, args);
    }

}
