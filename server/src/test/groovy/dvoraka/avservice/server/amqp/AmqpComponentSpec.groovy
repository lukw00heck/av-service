package dvoraka.avservice.server.amqp

import dvoraka.avservice.common.AvMessageListener
import dvoraka.avservice.common.Utils
import dvoraka.avservice.common.amqp.AvMessageMapper
import dvoraka.avservice.common.data.AvMessage
import dvoraka.avservice.common.data.DefaultAvMessage
import dvoraka.avservice.db.repository.MessageInfoRepository
import dvoraka.avservice.db.service.DefaultMessageInfoService
import org.springframework.amqp.core.AmqpTemplate
import org.springframework.amqp.core.Message
import spock.lang.Specification
import spock.lang.Subject

/**
 * AMQP component test.
 */
class AmqpComponentSpec extends Specification {

    @Subject
    AmqpComponent component

    AmqpTemplate amqpTemplate
    AvMessageMapper messageMapper


    def setup() {
        MessageInfoRepository infoRepository = Mock()
        DefaultMessageInfoService infoService = new DefaultMessageInfoService(infoRepository)
        amqpTemplate = Mock()

        component = new AmqpComponent("NONE", "TEST1", amqpTemplate, infoService)

        messageMapper = new AvMessageMapper()
    }

    def "on message"() {
        given:
            AvMessageListener listener = Mock()
            AvMessage message = Utils.genNormalMessage()
            Message amqpMsg = messageMapper.transform(message)

            component.addAvMessageListener(listener)

        when:
            component.onMessage(amqpMsg)

        then:
            1 * listener.onAvMessage(_)
    }

    def "on message with mapper exception"() {
        given:
            AvMessageListener listener = Mock()
            AvMessage message = Utils.genNormalMessage()
            Message amqpMsg = messageMapper.transform(message)
            amqpMsg.getMessageProperties().setType(null)

            component.addAvMessageListener(listener)

        when:
            component.onMessage(amqpMsg)

        then:
            0 * listener.onAvMessage(_)
    }

    def "on message with null"() {
        when:
            component.onMessage((Message) null)

        then:
            thrown(NullPointerException)
    }

    def "send null message"() {
        when:
            component.sendMessage(null)

        then:
            thrown(NullPointerException)
    }

    def "send normal message"() {
        given:
            AvMessage message = Utils.genNormalMessage()

        when:
            component.sendMessage(message)

        then:
            1 * amqpTemplate.convertAndSend(_, _, _)
    }

    // TODO: improve
    def "send broken message"() {
        given:
            AvMessage message = new DefaultAvMessage.Builder(null)
                    .build()

        when:
            component.sendMessage(message)

        then: "send error response"
            1 * amqpTemplate.convertAndSend(_, _, _)
    }

    def "add listeners"() {
        when:
            component.addAvMessageListener(getAvMessageListener())
            component.addAvMessageListener(getAvMessageListener())

        then:
            component.listenersCount() == 2
    }

    def "remove listeners"() {
        given:
            AvMessageListener listener1 = getAvMessageListener()
            AvMessageListener listener2 = getAvMessageListener()

        when:
            component.addAvMessageListener(listener1)
            component.addAvMessageListener(listener2)

        then:
            component.listenersCount() == 2

        when:
            component.removeAvMessageListener(listener1)
            component.removeAvMessageListener(listener2)

        then:
            component.listenersCount() == 0
    }

    def "add listeners from diff threads"() {
        given:
            int observers = 50

            Runnable addListener = {
                component.addAvMessageListener(getAvMessageListener())
            }

            Thread[] threads = new Thread[observers]
            for (int i = 0; i < threads.length; i++) {
                threads[i] = new Thread(addListener)
            }

        when:
            threads.each {
                it.start()
            }
            threads.each {
                it.join()
            }

        then:
            observers == component.listenersCount()
    }

    def "remove observers from different threads"() {
        given:
            int observers = 50

            AvMessageListener messageListener = getAvMessageListener()
            Runnable removeListener = {
                component.removeAvMessageListener(messageListener)
            }

            Thread[] threads = new Thread[observers]
            for (int i = 0; i < threads.length; i++) {
                threads[i] = new Thread(removeListener)
            }

            observers.times {
                component.addAvMessageListener(messageListener)
            }

        when:
            threads.each {
                it.start()
            }
            threads.each {
                it.join()
            }

        then:
            component.listenersCount() == 0
    }

    def "run wrong onMessage"() {
        when:
            component.onMessage((javax.jms.Message) null)

        then:
            thrown(UnsupportedOperationException)
    }

    AvMessageListener getAvMessageListener() {
        return new AvMessageListener() {
            @Override
            void onAvMessage(AvMessage message) {
            }
        }
    }
}
