package net.pusz.natstest.natstest.nats.mocks;

import java.time.Duration;
import java.util.Collection;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeoutException;

import io.nats.client.Connection;
import io.nats.client.Dispatcher;
import io.nats.client.Message;
import io.nats.client.MessageHandler;
import io.nats.client.Options;
import io.nats.client.Statistics;
import io.nats.client.Subscription;

/**
 * MockNatsConnection
 */
public class MockNatsConnection implements Connection {
    private final Dictionary<String, MessageHandler> handlers = new Hashtable<String, MessageHandler>();

    @Override
    public void publish(String subject, byte[] body) {
        var handler = this.handlers.get(subject);

        if (handler != null) {
            var message = MockNatsMessage.builder().data(body);
        }
    }

    @Override
    public void publish(String subject, String replyTo, byte[] body) {
    }

    @Override
    public CompletableFuture<Message> request(String subject, byte[] data) {
        return null;
    }

    @Override
    public Message request(String subject, byte[] data, Duration timeout) throws InterruptedException {
        return null;
    }

    @Override
    public Subscription subscribe(String subject) {
        return null;
    }

    @Override
    public Subscription subscribe(String subject, String queueName) {
        return null;
    }

    @Override
    public Dispatcher createDispatcher(MessageHandler handler) {
        var dispatcher = new MockNatsDispatcher(this.handlers);
        return dispatcher;
    }

    @Override
    public void closeDispatcher(Dispatcher dispatcher) {
    }

    @Override
    public void flush(Duration timeout) throws TimeoutException, InterruptedException {
    }

    @Override
    public CompletableFuture<Boolean> drain(Duration timeout) throws TimeoutException, InterruptedException {
        return null;
    }

    @Override
    public void close() throws InterruptedException {
    }

    @Override
    public Status getStatus() {
        return null;
    }

    @Override
    public long getMaxPayload() {
        return 0;
    }

    @Override
    public Collection<String> getServers() {
        return null;
    }

    @Override
    public Statistics getStatistics() {
        return null;
    }

    @Override
    public Options getOptions() {
        return null;
    }

    @Override
    public String getConnectedUrl() {
        return null;
    }

    @Override
    public String getLastError() {
        return null;
    }

    @Override
    public String createInbox() {
        return null;
    }
}