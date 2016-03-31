package dvoraka.avservice

import dvoraka.avservice.configuration.AppConfig
import dvoraka.avservice.server.AVServer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import spock.lang.Ignore
import spock.lang.Specification

/**
 * AMQP AV server test.
 */
// TODO: improve it
@Ignore
@ContextConfiguration(classes = [AppConfig])
@ActiveProfiles("amqp")
class AmqpAVServerISpec extends Specification {

    @Autowired
    AVServer avServer


    def setup() {
        // stop server
    }

    def "AV server loading"() {
        expect:
        avServer != null
    }

    def "status after start"() {
        when:
        avServer.start()

        then:
        avServer.isStarted()
    }

    def "status after stop"() {
        when:
        avServer.stop()

        then:
        avServer.isStopped()
    }
}
