package dvoraka.avservice.server;

import dvoraka.avservice.client.AvMessageSender;
import dvoraka.avservice.common.AvMessageListener;
import dvoraka.avservice.common.service.ServiceManagement;

/**
 * Anti-virus server interface.
 */
public interface AvServer extends ServiceManagement, AvMessageListener, AvMessageSender {
}
