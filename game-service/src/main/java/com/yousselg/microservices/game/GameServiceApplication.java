package com.yousselg.microservices.game;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class GameServiceApplication {

    public static void main(final String[] args) {
        SpringApplication.run(GameServiceApplication.class, args);
    }

}
