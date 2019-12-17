package net.pusz.natstest.natstest.nats;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;

import io.nats.client.Connection;
import net.pusz.natstest.natstest.nats.mocks.InMemoryNatsConnection;

@TestConfiguration
public class TestNatsConfig {
    @Primary
    @Bean
    public Connection natsTest(Environment env) {
        return new InMemoryNatsConnection();
    }

    @Bean
    public NotificationListenerTestHandler<TestNotification> TestListener() {
        return new NotificationListenerTestHandler<TestNotification>();
    }
}