package net.pusz.natstest.natstest.applicationnotifications;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a message published notification.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class MessagePublishedNotification {
    /**
     * Specifies a message.
     */
    private String message;
}
