package net.pusz.natstest.natstest.nats;

import org.springframework.context.event.EventListener;

import reactor.core.publisher.ReplayProcessor;

public class NotificationListenerTestAdapter {
    private ReplayProcessor<TestNotification> events = ReplayProcessor.create();

    public ReplayProcessor<TestNotification> getEvents() {
        return this.events;
    }

    @EventListener
    public void onNotification(final TestNotification notification) {
        this.events.onNext(notification);
    }
}