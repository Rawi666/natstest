package net.pusz.natstest.natstest.nats;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import io.nats.client.Connection;
import io.nats.client.Message;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ApplicationNotificationPublisherImpl implements ApplicationNotificationPublisher {
    private final String PubSubTopicName = "notification";
    private static final Charset MessageCharset = StandardCharsets.UTF_8;
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;
    @Autowired
    private Connection nats;
    private final List<Runnable> disposables = new ArrayList<Runnable>();

    @PostConstruct
    public void initialize() {
        final var dispatcher = this.nats.createDispatcher((msg) -> {
        });
        var topic = PubSubTopicName + ".>";
        log.debug("Subscribing to topic: {}", topic);
        final var subscription = dispatcher.subscribe(topic, (msg) -> onConsumerMessageReceived(msg));
        this.disposables.add(() -> dispatcher.unsubscribe(subscription));
    }

    private void onConsumerMessageReceived(final Message message) {
        try {
            var msg = new String(message.getData(), MessageCharset);
            final var topic = message.getSubject();
            log.debug("Received message from topic '{}': {}", topic, msg);

            var typeName = topic.substring(PubSubTopicName.length() + 1);
            var cls = Class.forName(typeName);
            var objectMapper = new ObjectMapper();
            var deserializedMsg = objectMapper.readValue(msg, cls);
            this.applicationEventPublisher.publishEvent(deserializedMsg);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
    }

    @Override
    @SneakyThrows
    public void publish(final Object notification) {
        var objectMapper = new ObjectMapper();
        var sb = new StringBuilder(PubSubTopicName).append(".").append(notification.getClass().getCanonicalName());
        var topic = sb.toString();
        var bytes = objectMapper.writeValueAsString(notification).getBytes(MessageCharset);
        log.debug("Publishing message to topic '{}'", topic);
        this.nats.publish(topic, bytes);
    }

    @PreDestroy
    public void dispose() {
        log.debug("Disposing {}", this.getClass().getName());
        for (final var disposable : disposables) {
            disposable.run();
        }
    }
}