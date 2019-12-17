package net.pusz.natstest.natstest.nats;

import org.springframework.context.event.EventListener;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
public class NotificationListenerTest<TNotification> {
    @Setter(value = AccessLevel.PRIVATE)
    private TNotification notification;

    @EventListener
    public void onNotification(final TNotification notification) {
        this.notification = notification;
    }
}