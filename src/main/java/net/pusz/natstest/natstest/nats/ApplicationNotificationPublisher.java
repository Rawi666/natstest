package net.pusz.natstest.natstest.nats;

/**
 * Publishes Application Notifications to every listener. Listeners should
 * handle notifications with @EventListerer annotation.
 */
@FunctionalInterface
public interface ApplicationNotificationPublisher {
    /**
     * Publishes a notification to application notification bus.
     * 
     * @param notification A notification to publish.
     */
    void publish(Object notification);
}