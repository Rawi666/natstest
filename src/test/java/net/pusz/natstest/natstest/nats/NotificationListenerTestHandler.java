package net.pusz.natstest.natstest.nats;

import org.springframework.context.event.EventListener;

import lombok.Getter;

public class NotificationListenerTestHandler<TNotification>  {
    @Getter
    private TNotification notification;

    @EventListener
    public void onNotification(final TNotification notification) {
        this.notification = notification;
    }
}