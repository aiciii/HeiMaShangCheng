package com.hmall.gateway.filters;

import lombok.Data;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
//把Objerct变成自己定义的参数类
public class PrintAnyGatewayFilterFactory extends AbstractGatewayFilterFactory<PrintAnyGatewayFilterFactory.Config> {
    @Override
    public GatewayFilter apply(Config config) {
        return new OrderedGatewayFilter(new GatewayFilter() {
            @Override
            public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
                System.out.println(config.getA() +""+ config.getB() +""+ config.getC());
                System.out.println("print any filter running");
                return chain.filter(exchange);
            }
        },1);
    }

    //定义自己filter的参数
    @Data
    public static class Config{
        private String a;
        private String b;
        private String c;
    }

    //把内部参数类写到构造方法
    public PrintAnyGatewayFilterFactory() {
        super(Config.class);
    }

    //新增变量顺序
    @Override
    public List<String> shortcutFieldOrder() {
        return List.of("a","b","c");
    }
}
