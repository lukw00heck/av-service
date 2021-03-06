package dvoraka.avservice.common.testing;

/**
 * Interface for performance test properties.
 */
public interface PerformanceTestProperties extends TestProperties {

    /**
     * Returns a message count for the test.
     *
     * @return the message count
     */
    long getMsgCount();

    /**
     * If the test sends a data without receiving.
     *
     * @return the send only flag
     */
    boolean isSendOnly();

    /**
     * Maximum rate per second.
     *
     * @return the rate
     */
    long getMaxRate();

    /**
     * Returns a number of threads for the test.
     *
     * @return the number of threads
     */
    int getThreadCount();
}
