package net.pusz.natstest.natstest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.pusz.natstest.natstest.applicationnotifications.MessagePublishedNotification;
import net.pusz.natstest.natstest.nats.ApplicationNotificationPublisher;

@Component
@Slf4j
public class CommandLine implements CommandLineRunner {
    @Autowired
    private Environment env;
    @Autowired
    private ApplicationNotificationPublisher applicationNotificationPublisher;

    @SneakyThrows
    @Override
    public void run(final String... args) {
        final var isProducer = env.getProperty("IS_PRODUCER");
        if (isProducer != null && (isProducer.equalsIgnoreCase("yes") || isProducer.equalsIgnoreCase("true"))) {
            log.info("-==Starting producer==-");

            final var msg = "MessageNumber#";

            for (int i = 0; i < 100; i++) {
                applicationNotificationPublisher
                        .publish(MessagePublishedNotification.builder().message(msg + i).build());
                Thread.sleep(1000);
            }
        } else {
            log.info("-==Starting consumer==-");
        }
    }

    @EventListener
    public void onNotification(MessagePublishedNotification notification) {
        log.info("Received notification: {}", notification.getMessage());
    }
}