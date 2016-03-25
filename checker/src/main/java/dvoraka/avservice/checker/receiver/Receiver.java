package dvoraka.avservice.checker.receiver;

import dvoraka.avservice.checker.exception.LastMessageException;
import dvoraka.avservice.checker.exception.ProtocolException;

import java.io.IOException;

/**
 * AMQP interface for receiving.
 *
 * @author dvoraka
 */
public interface Receiver {

    /**
     * Receives data.
     *
     * @param corrId parent message ID
     * @throws IOException
     * @throws InterruptedException
     * @throws ProtocolException
     * @throws LastMessageException
     */
    boolean receive(String corrId) throws
            IOException,
            InterruptedException,
            ProtocolException,
            LastMessageException;

    boolean getVerboseOutput();

    void setVerboseOutput(boolean verboseOutput);
}
