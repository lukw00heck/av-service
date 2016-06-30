package dvoraka.avservice.checker;

import dvoraka.avservice.checker.exception.BadExchangeException;
import dvoraka.avservice.checker.exception.ProtocolException;
import dvoraka.avservice.checker.exception.UnknownProtocolException;

/**
 * Object representation for AMQP anti-virus error message.
 */
public class ErrorMessage {

    private String errorText;
    private String errorType;

    /**
     * Creates an error message object from the string.
     *
     * @param rawMessage the error string from message
     * @throws IllegalArgumentException if <code>rawMessage</code> can't be parsed
     */
    public ErrorMessage(String rawMessage) {
        if (rawMessage == null) {
            throw new IllegalArgumentException("rawMessage can't be null");
        }

        errorText = rawMessage;
        errorType = rawMessage.split(":", 2)[0];
    }

    public void check() throws ProtocolException {
        // TODO: improve conditions
        if ("bad app-id".equalsIgnoreCase(getErrorType())) {
            throw new BadExchangeException(getErrorText());
        } else {
            throw new UnknownProtocolException(getErrorText());
        }
    }

    @Override
    public String toString() {
        return "Error message: " + getErrorType();
    }

    /**
     * @return the errorType
     */
    public String getErrorType() {
        return errorType;
    }

    /**
     * @return the errorText
     */
    public String getErrorText() {
        return errorText;
    }
}
