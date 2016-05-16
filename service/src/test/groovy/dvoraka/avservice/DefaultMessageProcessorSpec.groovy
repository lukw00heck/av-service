package dvoraka.avservice

import dvoraka.avservice.common.Utils
import dvoraka.avservice.common.data.AVMessage
import dvoraka.avservice.common.data.DefaultAVMessage
import dvoraka.avservice.common.data.MessageStatus
import dvoraka.avservice.exception.ScanErrorException
import dvoraka.avservice.server.ReceivingType
import dvoraka.avservice.service.AVService
import org.springframework.test.util.ReflectionTestUtils
import spock.lang.Specification

/**
 * Default processor tests.
 */
class DefaultMessageProcessorSpec extends Specification {

    DefaultMessageProcessor processor


    def setup() {
        processor = new DefaultMessageProcessor(2)
    }

    def cleanup() {
        if (processor != null) {
            processor.cleanup()
        }
    }

    def "constructor (thread count)"() {
        setup:
        int threadCount = 5
        processor = new DefaultMessageProcessor(threadCount)

        expect:
        processor.getThreadCount() == threadCount
        processor.getQueueSize() == DefaultMessageProcessor.DEFAULT_QUEUE_SIZE
        processor.getServerReceivingType() ==
                DefaultMessageProcessor.DEFAULT_RECEIVING_TYPE
    }

    def "constructor (thread count, rec. type, queue size)"() {
        setup:
        int threadCount = 5
        ReceivingType receivingType = ReceivingType.LISTENER
        int queueSize = 10
        processor = new DefaultMessageProcessor(
                threadCount,
                receivingType,
                queueSize)

        expect:
        processor.getThreadCount() == threadCount
        processor.getServerReceivingType() == receivingType
        processor.getQueueSize() == queueSize
    }

    def "send normal message"() {
        setup:
        AVService service = Stub()
        service.scanStream(_) >> false

        ReflectionTestUtils.setField(processor, null, service, AVService.class)

        AVMessage message = Utils.genNormalMessage()
        processor.sendMessage(message)

        sleep(1000)

        expect:
        processor.hasProcessedMessage()
        processor.getProcessedMessage().getCorrelationId().equals(message.getId())
    }

    def "send message with a full queue"() {
        setup:
        long time = 1000
        AVService service = Stub()
        service.scanStream(_) >> false

        processor = new DefaultMessageProcessor(2, ReceivingType.POLLING, 1)
        ReflectionTestUtils.setField(processor, null, service, AVService.class)

        AVMessage message1 = Utils.genNormalMessage()
        processor.sendMessage(message1)
        AVMessage message2 = Utils.genNormalMessage()
        processor.sendMessage(message2)

        sleep(time)

        expect:
        processor.isProcessedQueueFull()
        processor.hasProcessedMessage()
        processor.getProcessedMessage()

        and:
        sleep(time)
        processor.hasProcessedMessage()
        processor.getProcessedMessage()
        sleep(time)
        !processor.isProcessedQueueFull()
    }

    def "send message (with a service error)"() {
        setup:
        AVService service = Stub()
        service.scanStream(_) >> {
            throw new ScanErrorException("Service is dead")
        }

        ReflectionTestUtils.setField(processor, null, service, AVService.class)
        AVMessage message = Utils.genNormalMessage()

        processor.sendMessage(message)

        sleep(1000)

        expect:
        // TODO: catch an exception
        true
    }

    def "processing message status"() {
        setup:
        String testId = "testId"

        AVService service = Stub()
        service.scanStream(_) >> {
            sleep(1000)
            return false
        }

        ReflectionTestUtils.setField(processor, null, service, AVService.class)

        when:
        processor.sendMessage(new DefaultAVMessage.Builder(testId).build())

        then:
        processor.messageStatus(testId) == MessageStatus.PROCESSING
    }

    def "processed message status"() {
        setup:
        String testId = "testId"

        AVService service = Stub()
        ReflectionTestUtils.setField(processor, null, service, AVService.class)

        when:
        processor.sendMessage(new DefaultAVMessage.Builder(testId).build())
        sleep(1000)

        then:
        processor.messageStatus(testId) == MessageStatus.PROCESSED
    }

    def "unknown message status"() {
        setup:
        String testId = "testId"

        AVService service = Stub()
        ReflectionTestUtils.setField(processor, null, service, AVService.class)

        expect:
        processor.messageStatus(testId) == MessageStatus.UNKNOWN
    }
}
