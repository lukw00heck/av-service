package dvoraka.avservice.stats;

import dvoraka.avservice.common.data.AvMessageSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

/**
 * Statistics service.
 */
@Service
public class DefaultStatsService implements StatsService {

    private final Messages messages;


    @Autowired
    public DefaultStatsService(Messages messages) {
        this.messages = messages;
    }

    @Override
    public long todayCount() {
        LocalDate today = LocalDate.now();
        Instant start = today.atStartOfDay(ZoneId.systemDefault()).toInstant();

        return messages
                .when(start, Instant.now())
                .filter(info -> info.getSource().equals(AvMessageSource.PROCESSOR))
                .count();
    }
}