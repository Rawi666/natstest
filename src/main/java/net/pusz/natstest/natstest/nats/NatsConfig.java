package net.pusz.natstest.natstest.nats;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import io.nats.client.Connection;
import io.nats.client.Nats;

/**
 * Holds configuration beans specific for NATS messaging.
 */
@Configuration
public class NatsConfig {
    /**
     * Returns a connection to NATS using environmental variable NATS_SERVER.
     * Fallbacks to nats://localhost:4222 if NATS_SERVER env is not defined.
     */
    @Bean
    public Connection nats(Environment env) {
        Connection nats = null;
        try {
            nats = Nats.connect(env.getProperty("NATS_SERVER", "nats://localhost:4222"));
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return nats;
    }
}