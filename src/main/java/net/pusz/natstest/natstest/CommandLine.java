package net.pusz.natstest.natstest;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import io.nats.client.Connection;
import io.nats.client.Message;

@Component
public class CommandLine implements CommandLineRunner, AutoCloseable {
    private static Logger Log = LoggerFactory.getLogger(CommandLine.class);
    private final String PubSubTopicName = "PublishSubscribeTopicName";
    private final String ReqRespTopicName = "RequestResponseTopicName";
    private static final Charset MessageCharset = StandardCharsets.UTF_8;
    private List<Runnable> disposables = new ArrayList<Runnable>();
    private Connection nats;
    private int requestsReceived;
    private int publishMessagesReceived;
    private Environment env;

    public CommandLine(final Connection nats, final Environment env) {
        super();
        this.nats = nats;
        this.env = env;
    }

    @Override
    public void run(final String... args) throws Exception {
        final var isProducer = env.getProperty("IS_PRODUCER");
        if (isProducer != null && isProducer.equalsIgnoreCase("yes")) {
            Log.info("-==Starting producer==-");

            var dispatcher = this.nats.createDispatcher((msg) -> {
            });
            var subscription = dispatcher.subscribe(ReqRespTopicName, (msg) -> onProducerReqRespMessageReceived(msg));
            this.disposables.add(() -> dispatcher.unsubscribe(subscription));

            final var msg = "MessageNumber#";

            for (int i = 0; i < 100; i++) {
                this.nats.publish(PubSubTopicName, (msg + i).getBytes(MessageCharset));
                Thread.sleep(1000);
            }
        } else {
            Log.info("-==Starting consumer==-");
            var dispatcher = this.nats.createDispatcher((msg) -> {
            });
            var subscription = dispatcher.subscribe(PubSubTopicName, (msg) -> onConsumerMessageReceived(msg));
            this.disposables.add(() -> dispatcher.unsubscribe(subscription));
        }
    }

    private void onProducerReqRespMessageReceived(Message msg) {
        var response = new String(msg.getData(), MessageCharset);
        var topic = msg.getSubject();
        var replyTo = msg.getReplyTo();
        this.requestsReceived++;
        Log.info("Received message from topic '{}': {}. Response will be sent to '{}'", topic, response, replyTo);
        this.nats.publish(replyTo, ("Response number " + this.requestsReceived).getBytes(MessageCharset));
    }

    private void onConsumerMessageReceived(Message msg) {
        var response = new String(msg.getData(), MessageCharset);
        var topic = msg.getSubject();
        publishMessagesReceived++;
        Log.info("Received message from topic '{}': {}", topic, response);

        if (this.publishMessagesReceived % 5 == 0) {
            Log.info("5 broadcast messages received. Sending request-response");
            Future<Message> incoming = this.nats.request(ReqRespTopicName,
                    ("Request from consumer " + publishMessagesReceived).getBytes(MessageCharset));
            Message responseMsg;

            try {
                responseMsg = incoming.get(500, TimeUnit.MILLISECONDS);
                response = new String(responseMsg.getData(), MessageCharset);
                Log.info("Received response from publisher: {}", response);
            } catch (InterruptedException | ExecutionException | TimeoutException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void close() throws Exception {
        Log.info("Disposing");
        for (var disposable : disposables) {
            disposable.run();
        }
    }

}