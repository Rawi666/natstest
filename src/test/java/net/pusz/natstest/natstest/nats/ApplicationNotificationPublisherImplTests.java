package net.pusz.natstest.natstest.nats;

import java.time.Duration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { TestNatsConfig.class })
public class ApplicationNotificationPublisherImplTests {
    @Autowired
    private ApplicationNotificationPublisher applicationNotificationPublisher;

    @Autowired
    private NotificationListenerTestAdapter notificationListener;

    @Test
    @DisplayName("Should Receive Notification")
    public void shouldReceiveNotification() {
        var expectedMessage = "some message payload";
        var msg = TestNotification.builder().message(expectedMessage).build();

        this.applicationNotificationPublisher.publish(msg);
        var actual = notificationListener.getEvents().blockFirst(Duration.ofSeconds(1));
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(expectedMessage, actual.getMessage());
    }
}
