package com.aifei;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@SpringBootApplication
@EnableHystrix
@EnableEurekaClient
@RestController
public class HystrixApplication {

    @Autowired
    RestTemplate restTemplate;

    public static void main(String[] args) {
        SpringApplication.run(HystrixApplication.class, args);
    }

    /**
     * 实例化RestTemplate
     *
     * @return
     */
    @LoadBalanced
    @Bean
    public RestTemplate rest() {
        return new RestTemplate();
    }

    /**
     * 调用 user微服务
     */
    //服务熔断
    //双击俩下Shift 输入HystrixCommandProperties 里面有详细的属性和介绍
    @HystrixCommand(fallbackMethod = "getDefaultUser", commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),//是否开启断路器
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),//请求次数
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),//时间窗口期
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60"),//失败率达到60%后跳闸
    })
    @GetMapping("hystrixUser")
    public String hystrixUser(@RequestParam Integer id) {
        if (id < 0) {
            throw new RuntimeException("###id 不能负数###");
        }
        String data = restTemplate.getForObject("http://service-provider/getHystrix?id=" + id, String.class);
        return data;
    }

    public String getDefaultUser() {
        return "{\"id\":-1,\"name\":\"熔断用户\",\"password\":\"123456\"}";
    }

}
