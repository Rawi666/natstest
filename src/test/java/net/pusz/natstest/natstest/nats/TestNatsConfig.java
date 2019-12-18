package net.pusz.natstest.natstest.nats;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import io.nats.client.Connection;
import net.pusz.natstest.natstest.nats.mocks.InMemoryNatsConnection;

@Configuration
public class TestNatsConfig {
    @Bean
    public Connection natsTest(Environment env) {
        return new InMemoryNatsConnection();
    }

    @Bean
    public NotificationListenerTestAdapter testListener() {
        return new NotificationListenerTestAdapter();
    }

    @Bean
    public ApplicationNotificationPublisher applicationNotificationPublisher() {
        return new ApplicationNotificationPublisherImpl();
    }
}