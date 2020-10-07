package com.aifei.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Date;

@Component
@Slf4j
public class MyLogGatewayFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("**********come in MyLogGatewayFilter"+new Date());
        String uname = exchange.getRequest().getQueryParams().getFirst("id");//请求参数必须包含id=xxx （顺序可变）
        if(uname == null){
            log.info("******用户名为null，非法用户，o(╥﹏╥)o");
            exchange.getResponse().setStatusCode(HttpStatus.NOT_ACCEPTABLE);
            return exchange.getResponse().setComplete();
        }
        return chain.filter(exchange);
    }
    //ture:=http://localhost:9085/consumer/goto/CloudData?uname=123
    //false:=http://localhost:9085/consumer/goto/CloudData?uname

    @Override
    public int getOrder() {
        return 0;
    }
}
