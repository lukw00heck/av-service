package dvoraka.avservice.server.configuration.bridge

import dvoraka.avservice.server.ServerComponentBridge
import dvoraka.avservice.server.configuration.BridgeConfig
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import spock.lang.Ignore
import spock.lang.Specification

/**
 * Configuration test.
 */
@ContextConfiguration(classes = [BridgeConfig.class])
@DirtiesContext
@ActiveProfiles(['server', 'bridge', 'amqp2jms', 'no-db'])
@Ignore('WIP')
class AmqpToJmsConfigNewISpec extends Specification {

    @Autowired
    ServerComponentBridge bridge


    def "test"() {
        expect:
            true
    }
}
