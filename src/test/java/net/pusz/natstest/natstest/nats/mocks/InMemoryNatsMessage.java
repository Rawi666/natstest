package net.pusz.natstest.natstest.nats.mocks;

import io.nats.client.Connection;
import io.nats.client.Message;
import io.nats.client.Subscription;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InMemoryNatsMessage implements Message {
    private String subject;
    private byte[] data;
    private String replyTo;
    private Subscription subscription;
    private String SID;
    private Connection connection;
}