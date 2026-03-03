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
        return new OrderedGatewayFilter((exchange, chain) -> {
            String path = exchange.getRequest().getPath().toString();
            System.out.println("【局部过滤器】开始执行，请求路径：" + path);
            System.out.println("【局部过滤器】配置参数：" + config.getA() + config.getB() + config.getC());
            System.out.println("【局部过滤器】print any filter running");

            return chain.filter(exchange).doOnSuccess(aVoid -> {
                System.out.println("【局部过滤器】请求转发完成，路径：" + path);
            });
        }, 1);
    }

    @Data
    public static class Config{
        private String a;
        private String b;
        private String c;
    }

    public PrintAnyGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return List.of("a","b","c");
    }
}
