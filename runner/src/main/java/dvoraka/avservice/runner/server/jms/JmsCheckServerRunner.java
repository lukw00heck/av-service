package dvoraka.avservice.runner.server.jms;

import dvoraka.avservice.common.runner.AbstractServiceRunner;
import dvoraka.avservice.common.runner.ServiceRunner;
import dvoraka.avservice.common.service.ServiceManagement;
import dvoraka.avservice.server.BasicAvServer;
import dvoraka.avservice.server.configuration.ServerConfig;

/**
 * JMS AV check server runner.
 */
public class JmsCheckServerRunner extends AbstractServiceRunner {

    public static void main(String[] args) {
        ServiceRunner runner = new JmsCheckServerRunner();
        runner.run();
    }

    @Override
    public String[] profiles() {
        return new String[]{"core", "check", "server", "jms", "db"};
    }

    @Override
    public Class<?>[] configClasses() {
        return new Class<?>[]{ServerConfig.class};
    }

    @Override
    public Class<? extends ServiceManagement> runClass() {
        return BasicAvServer.class;
    }
}
