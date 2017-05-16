package dvoraka.avservice.client.service.response

import dvoraka.avservice.client.ServerComponent
import dvoraka.avservice.common.Utils
import dvoraka.avservice.common.data.AvMessage
import spock.lang.Specification
import spock.lang.Subject

/**
 * Client spec.
 */
class DefaultResponseClientSpec extends Specification {

    @Subject
    DefaultResponseClient client

    ServerComponent serverComponent


    def setup() {
        serverComponent = Mock()
        client = new DefaultResponseClient(serverComponent)
    }

    def "start and stop"() {
        expect:
            !client.isStarted()

        when:
            client.start()

        then:
            client.isStarted()
            1 * serverComponent.addAvMessageListener(_)

        when: "start again"
            client.start()

        then:
            0 * serverComponent.addAvMessageListener(_)

        when:
            client.stop()

        then:
            !client.isStarted()
            1 * serverComponent.removeAvMessageListener(_)

        when: "stop again"
            client.stop()

        then:
            0 * serverComponent.removeAvMessageListener(_)
    }

    def "get response before start"() {
        when:
            client.getResponse('response')

        then:
            thrown(IllegalStateException)
    }

    def "get random response"() {
        when:
            client.start()

        then:
            client.getResponse('response') == null
    }

    def "test cache"() {
        given:
            AvMessage response = Utils.genFileMessage()

        when:
            client.start()
            client.onAvMessage(response)

        then:
            client.getResponse(response.getCorrelationId()) == response
    }
}