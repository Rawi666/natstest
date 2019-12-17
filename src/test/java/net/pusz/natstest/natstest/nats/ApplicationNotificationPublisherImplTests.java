package net.pusz.natstest.natstest.nats;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.Duration;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(classes = { TestNatsConfig.class }, webEnvironment = WebEnvironment.NONE)
public class ApplicationNotificationPublisherImplTests {
    @Autowired
    private ApplicationNotificationPublisherImpl applicationNotificationPublisher;

    @Autowired
    private NotificationListenerTestHandler notificationListener;

    @Test
    @DisplayName("Should Receive Notification")
    public void shouldReceiveNotification() {
        var expectedMessage = "some message payload";
        var msg = TestNotification.builder().message(expectedMessage).build();

        this.applicationNotificationPublisher.publish(msg);
        var actual = notificationListener.getEvents().blockFirst(Duration.ofSeconds(1));
        assertNotNull(actual);
        assertEquals(expectedMessage, actual.getMessage());
    }
}
