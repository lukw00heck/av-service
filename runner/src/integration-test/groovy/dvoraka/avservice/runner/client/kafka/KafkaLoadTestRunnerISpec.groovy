package dvoraka.avservice.runner.client.kafka

import spock.lang.Specification

class KafkaLoadTestRunnerISpec extends Specification {

    def setupSpec() {
        System.setProperty('avservice.perf.msgCount', '2')
    }

    def "Run Kafka load test runner"() {
        when:
            KafkaLoadTestRunner.main([] as String[])

        then:
            notThrown(Exception)
    }
}
