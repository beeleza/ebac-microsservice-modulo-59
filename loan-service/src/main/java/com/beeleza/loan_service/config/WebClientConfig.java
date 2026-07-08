package com.beeleza.loan_service.config;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Configuration
public class WebClientConfig {

    @Bean
    @LoadBalanced
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(
                        HttpClient.create()
                                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 2000)
                                .responseTimeout(Duration.ofSeconds(5))
                                .doOnConnected(conn ->
                                        conn.addHandlerLast(new ReadTimeoutHandler(5, TimeUnit.SECONDS))
                                )
                ));
    }

    @Bean
    public WebClient webClient(@LoadBalanced WebClient.Builder builder) {
        return builder.build();
    }
}
