package com.hmall.gateway.filters;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class MyGlobalFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        // 新增：打印请求路径，明确是哪个请求触发的过滤器
        String path = request.getPath().toString();
        System.out.println("【全局过滤器】开始执行，请求路径：" + path);

        HttpHeaders headers = request.getHeaders();
        System.out.println("【全局过滤器】headers：" + headers);

        // 新增：过滤器执行完成后的日志（异步）
        return chain.filter(exchange).doOnSuccess(aVoid -> {
            System.out.println("【全局过滤器】请求转发完成，路径：" + path);
        });
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
