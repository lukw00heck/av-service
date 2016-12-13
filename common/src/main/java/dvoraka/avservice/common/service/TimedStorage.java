package dvoraka.avservice.common.service;

import dvoraka.avservice.common.CustomThreadFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * Timed storage for a temporary data saving. It is up to client to delete the data but
 * there is a max time too. If you forget to clean, everything is still fine.
 * <p>
 * Thread-safe.
 */
public class TimedStorage<T> {

    private static final Logger log = LogManager.getLogger(TimedStorage.class);

    private static final long MAX_TIME = 60_000;

    /**
     * Maximum time in milliseconds.
     */
    private long maxTime;
    private volatile boolean running;
    private final ConcurrentHashMap<T, Long> storage;
    private final ExecutorService executorService;

    public TimedStorage() {
        this(MAX_TIME);
    }

    public TimedStorage(long maxTime) {
        this.maxTime = maxTime;
        storage = new ConcurrentHashMap<>();

        running = true;
        ThreadFactory threadFactory = new CustomThreadFactory("storage-cleaner-");
        executorService = Executors.newSingleThreadExecutor(threadFactory);
        executorService.execute(this::cleanStorage);
    }

    private void cleanStorage() {
        while (running) {
            long now = System.currentTimeMillis();
            storage.entrySet()
                    .removeIf(entry -> (entry.getValue() + maxTime) < now);

            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    public void put(T data) {
        storage.put(data, System.currentTimeMillis());
    }

    public boolean contains(T data) {
        return storage.containsKey(data);
    }

    public long remove(T data) {
        return Optional.ofNullable(data)
                .map(storage::remove)
                .orElse(0L);
    }

    public long size() {
        return storage.size();
    }

    public void stop() {
        running = false;

        final long timeout = 5;
        log.info("Stopping thread pool...");
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(timeout, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
                if (!executorService.awaitTermination(timeout, TimeUnit.SECONDS)) {
                    log.warn("Can't stop thread pool!");
                }
            }
        } catch (InterruptedException e) {
            log.warn("Stopping interrupted!", e);
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
