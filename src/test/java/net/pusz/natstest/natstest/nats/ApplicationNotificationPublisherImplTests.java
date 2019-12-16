package net.pusz.natstest.natstest.nats;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ContextConfiguration;

import io.nats.client.Connection;

@ContextConfiguration
@SpringBootTest
public class ApplicationNotificationPublisherImplTests {
    @Autowired
    private ApplicationNotificationPublisherImpl applicationNotificationPublisher;
    private TestNotification notification;

    @Test
    public void publishAndSubscribeTest() {
        var testMessage = "some message payload";
        var msg = TestNotification.builder().message(testMessage).build();
        this.applicationNotificationPublisher.publish(msg);

    }

    @EventListener
    public void onTestNotification(TestNotification notification) {
        this.notification = notification;
    }

    @Configuration
    public static class Context {
        @Bean
        public Connection nats(Environment env) {
            return new MockNatsConnection();
        }

    }
}