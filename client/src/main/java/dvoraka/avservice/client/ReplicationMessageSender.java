package dvoraka.avservice.client;

import dvoraka.avservice.common.data.replication.ReplicationMessage;

/**
 * Interface for replication message sending.
 */
@FunctionalInterface
public interface ReplicationMessageSender extends MessageSender<ReplicationMessage> {
}
