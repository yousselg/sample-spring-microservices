package com.yousselg.microservices.player;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class PlayerServiceApplication {

    public static void main(final String[] args) {
        SpringApplication.run(PlayerServiceApplication.class, args);
    }

}
