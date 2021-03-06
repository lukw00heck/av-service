package dvoraka.avservice.client.receive;

import dvoraka.avservice.common.data.replication.ReplicationMessage;
import dvoraka.avservice.common.listener.ReplicationMessageListener;

/**
 * Interface for replication message receiving.
 */
public interface ReplicationMessageReceiver extends
        MessageReceiver<ReplicationMessage, ReplicationMessageListener> {
}
