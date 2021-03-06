package dvoraka.avservice.runner.server;

import dvoraka.avservice.common.runner.AbstractServiceRunner;
import dvoraka.avservice.common.runner.ServiceRunner;
import dvoraka.avservice.common.service.ServiceManagement;
import dvoraka.avservice.server.BasicAvServer;
import dvoraka.avservice.server.configuration.ServerConfig;

/**
 * Custom server runner. You can choose custom configurations and profiles for your use cases.
 */
public class CustomServerRunner extends AbstractServiceRunner {

    public static void main(String[] args) {
        ServiceRunner runner = new CustomServerRunner();
        runner.run();
    }

    @Override
    public String[] profiles() {
        return new String[]{"core", "check", "server", "amqp", "no-db"};
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
