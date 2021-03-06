package dvoraka.avservice.client.configuration

import dvoraka.avservice.client.perf.BufferedPerformanceTester
import dvoraka.avservice.common.testing.PerformanceTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

/**
 * Configuration test.
 */
@ContextConfiguration(classes = [ClientConfig.class])
@ActiveProfiles(['client', 'kafka', 'performance', 'buffered', 'file-client', 'no-db'])
@DirtiesContext
class KafkaBufferedPerformanceTestConfigISpec extends Specification {

    @Autowired
    PerformanceTest tester


    def "test"() {
        expect:
            tester instanceof BufferedPerformanceTester
    }
}
