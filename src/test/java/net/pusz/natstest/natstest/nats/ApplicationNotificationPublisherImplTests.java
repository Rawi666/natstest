package net.pusz.natstest.natstest.nats;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;

@Component
@SpringBootTest(classes = { TestNatsConfig.class })
public class ApplicationNotificationPublisherImplTests {
    @Autowired
    private ApplicationNotificationPublisherImpl applicationNotificationPublisher;

    @Autowired
    private NotificationListenerTest<TestNotification> notificationListener;

    @Test
    public void publishAndSubscribeTest() {
        var testMessage = "some message payload";
        var msg = TestNotification.builder().message(testMessage).build();
        this.applicationNotificationPublisher.publish(msg);
        var notification = notificationListener.getNotification();
        assertNotNull(notification);
        assertEquals(testMessage, notification.getMessage());
    }
}
