package com.aifei;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@EnableDiscoveryClient
@RefreshScope
@SpringBootApplication
public class NacosConfigApplication {

    public static void main(String[] args) {
        SpringApplication.run(NacosConfigApplication.class, args);
    }

    @Value("${nacos.config}")
    private String config;

    @RequestMapping("/getConfig")
    public String getConfig() {
        return config;
    }

    /*@Value("${data.env}")
    private String env;

    @Value("${data.user.name}")
    private String username;

    @Value("${data.user.pwd}")
    private String password;

    @GetMapping("/getValue")
    public Map getValue() {
        Map<String,Object> map = new HashMap<>();
        map.put("env",env);
        map.put("username",username);
        map.put("password",password);
        return map;
    }*/
}
