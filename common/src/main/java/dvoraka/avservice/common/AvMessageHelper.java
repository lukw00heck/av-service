package dvoraka.avservice.common;

import dvoraka.avservice.common.data.AvMessage;

import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Helper interface for AV messages.
 */
public interface AvMessageHelper {

    /**
     * Notifies a message to all listeners.
     *
     * @param listeners the listeners
     * @param message   the message
     */
    default void notifyListeners(Iterable<AvMessageListener> listeners, AvMessage message) {
        listeners.forEach(listener -> listener.onAvMessage(message));
    }

    /**
     * Prepares a response with an info.
     *
     * @param message   the origin message
     * @param virusInfo the info
     * @return new response
     */
    default AvMessage prepareResponse(AvMessage message, String virusInfo) {
        return message.createResponse(virusInfo);
    }

    /**
     * Prepares an error response with an error info.
     *
     * @param message      the origin message
     * @param errorMessage the error info
     * @return new response
     */
    default AvMessage prepareErrorResponse(AvMessage message, String errorMessage) {
        return message.createErrorResponse(errorMessage);
    }

    /**
     * Checks if all conditions in a stream are true.
     *
     * @param conditions the stream of conditions
     * @param data       the data for an evaluation
     * @return the result
     */
    default boolean checkConditions(
            Stream<Predicate<? super AvMessage>> conditions,
            AvMessage data
    ) {
        return conditions.allMatch(condition -> condition.test(data));
    }
}
