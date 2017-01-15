package dvoraka.avservice.server.configuration.jms

import dvoraka.avservice.server.ServerComponent
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

/**
 * Configuration test.
 */
@ContextConfiguration(classes = [JmsConfig.class])
@ActiveProfiles(['core', 'jms', 'jms-rest', 'no-db'])
class JmsRestConfigISpec extends Specification {

    @Autowired
    ServerComponent serverComponent


    def "test"() {
        expect:
            true
    }
}