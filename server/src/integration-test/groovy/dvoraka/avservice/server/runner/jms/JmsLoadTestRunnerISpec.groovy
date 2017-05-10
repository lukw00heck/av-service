package dvoraka.avservice.server.runner.jms

import spock.lang.Specification

/**
 * Test for load test running.
 */
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