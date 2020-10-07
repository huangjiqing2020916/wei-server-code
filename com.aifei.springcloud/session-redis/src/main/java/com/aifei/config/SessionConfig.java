package com.aifei.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@Configuration
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 1120)//单位/秒 开启全局RedisSession会话管理机制
public class SessionConfig {

    @Value("${redis.hostname}")
    private String hostname;

    @Value("${redis.port}")
    private int port;


    @Bean
    public JedisConnectionFactory connectionFactory() {
        JedisConnectionFactory connectionFactory = new JedisConnectionFactory();
        connectionFactory.setPort(port);
        connectionFactory.setHostName(hostname);
        connectionFactory.setPassword("123456");
        return connectionFactory;
    }

}
