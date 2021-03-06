package dvoraka.avservice.storage.configuration;

import dvoraka.avservice.db.repository.db.DbFileRepository;
import dvoraka.avservice.storage.service.DbFileService;
import dvoraka.avservice.storage.service.FileService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Storage DB configuration for import.
 */
@Configuration
@Profile({"db", "db-mem"})
public class DbStorageConfig {

    @Bean
    public FileService dbFileService(DbFileRepository dbFileRepository) {
        return new DbFileService(dbFileRepository);
    }
}
