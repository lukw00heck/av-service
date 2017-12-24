package dvoraka.avservice.runner.client.jms

import spock.lang.Specification

class JmsLoadTestRunnerISpec extends Specification {

    def setupSpec() {
        System.setProperty('avservice.perf.msgCount', '2')
    }

    def "Run JMS load test runner"() {
        when:
            JmsLoadTestRunner.main([] as String[])

        then:
            notThrown(Exception)
    }
}
