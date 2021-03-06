package dvoraka.avservice.runner.client.amqp

import spock.lang.Specification


class AmqpLoadTestRunnerISpec extends Specification {

    def setupSpec() {
        System.setProperty('avservice.perf.msgCount', '2')
    }

    def "Run AMQP load test runner"() {
        when:
            AmqpLoadTestRunner.main([] as String[])

        then:
            notThrown(Exception)
    }
}
