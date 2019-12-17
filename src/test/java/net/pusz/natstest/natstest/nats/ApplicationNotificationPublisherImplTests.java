package net.pusz.natstest.natstest.nats;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
    private NotificationListenerTestHandler<TestNotification> notificationListener;

    @Test
    @DisplayName("Should Receive Notification")
    public void shouldReceiveNotification() {
        var testMessage = "some message payload";
        var msg = TestNotification.builder().message(testMessage).build();
        this.applicationNotificationPublisher.publish(msg);
        var notification = notificationListener.getNotification();
        assertNotNull(notification);
        assertEquals(testMessage, notification.getMessage());
    }
}
