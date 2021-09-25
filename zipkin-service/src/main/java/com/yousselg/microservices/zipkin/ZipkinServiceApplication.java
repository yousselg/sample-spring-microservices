package com.yousselg.microservices.zipkin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import zipkin.server.EnableZipkinServer;

@SpringBootApplication
@EnableZipkinServer
public class ZipkinServiceApplication {

    public static void main(final String[] args) {
        SpringApplication.run(ZipkinServiceApplication.class, args);
    }

}
