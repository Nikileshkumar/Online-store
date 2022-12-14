package com.example.orchestration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableKafka
public class OrderOrchestrationApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderOrchestrationApplication.class, args);
    }

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}
