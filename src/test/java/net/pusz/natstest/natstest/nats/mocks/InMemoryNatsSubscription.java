package net.pusz.natstest.natstest.nats.mocks;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;

import io.nats.client.Dispatcher;
import io.nats.client.Message;
import io.nats.client.Subscription;

public class InMemoryNatsSubscription implements Subscription {

    @Override
    public void setPendingLimits(long maxMessages, long maxBytes) {
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
    public CompletableFuture<Boolean> drain(Duration timeout) throws InterruptedException {
        return null;
    }

    @Override
    public String getSubject() {
        return null;
    }

    @Override
    public String getQueueName() {
        return null;
    }

    @Override
    public Dispatcher getDispatcher() {
        return null;
    }

    @Override
    public Message nextMessage(Duration timeout) throws InterruptedException, IllegalStateException {
        return null;
    }

    @Override
    public void unsubscribe() {
    }

    @Override
    public Subscription unsubscribe(int after) {
        return null;
    }
}