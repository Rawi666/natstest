package net.pusz.natstest.natstest.nats.mocks;

import java.time.Duration;
import java.util.Dictionary;
import java.util.concurrent.CompletableFuture;

import io.nats.client.Dispatcher;
import io.nats.client.MessageHandler;
import io.nats.client.Subscription;

public class MockNatsDispatcher implements Dispatcher {

    private Dictionary<String, MessageHandler> handlers;

    public MockNatsDispatcher(final Dictionary<String, MessageHandler> handlers) {
        this.handlers = handlers;
    }

    @Override
    public void setPendingLimits(final long maxMessages, final long maxBytes) {
    }

    @Override
    public long getPendingMessageLimit() {
        return 0;
    }

    @Override
    public long getPendingByteLimit() {
        return 0;
    }

    @Override
    public long getPendingMessageCount() {
        return 0;
    }

    @Override
    public long getPendingByteCount() {
        return 0;
    }

    @Override
    public long getDeliveredCount() {
        return 0;
    }

    @Override
    public long getDroppedCount() {
        return 0;
    }

    @Override
    public void clearDroppedCount() {
    }

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public CompletableFuture<Boolean> drain(final Duration timeout) throws InterruptedException {
        return null;
    }

    @Override
    public Dispatcher subscribe(final String subject) {
        return null;
    }

    @Override
    public Dispatcher subscribe(final String subject, final String queue) {
        return null;
    }

    @Override
    public Subscription subscribe(final String subject, final MessageHandler handler) {
        handlers.put(subject, handler);
        return new MockNatsSubscription();
    }

    @Override
    public Subscription subscribe(final String subject, final String queue, final MessageHandler handler) {
        return null;
    }

    @Override
    public Dispatcher unsubscribe(final String subject) {
        return null;
    }

    @Override
    public Dispatcher unsubscribe(final Subscription subscription) {
        return null;
    }

    @Override
    public Dispatcher unsubscribe(final String subject, final int after) {
        return null;
    }

    @Override
    public Dispatcher unsubscribe(final Subscription subscription, final int after) {
        return null;
    }

}