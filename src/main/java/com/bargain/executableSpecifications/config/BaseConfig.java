package com.bargain.executableSpecifications.config;

import com.bargain.notification.client.ChannelClient;
import com.bargain.users.client.AuthClient;
import com.bargain.users.client.SubscriptionClient;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class BaseConfig {

    @Value("${user-service.url}")
    private String userServiceUrl;

    @Value("${notification-service.url}")
    private String notificationServiceUrl;

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    public Faker faker() {
        return new Faker();
    }

    @Bean
    public AuthClient authClient(RestTemplate restTemplate) {
        return new AuthClient(restTemplate, userServiceUrl);
    }

    @Bean
    public SubscriptionClient subscriptionClient(RestTemplate restTemplate) {
        return new SubscriptionClient(restTemplate, userServiceUrl);
    }

    @Bean
    public ChannelClient channelClient(RestTemplate restTemplate) {
        return new ChannelClient(restTemplate, notificationServiceUrl);
    }
}
