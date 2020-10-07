package com.aifei;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@EnableConfigServer
@SpringBootApplication
public class ConfigSingleClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConfigSingleClientApplication.class, args);
    }

}
