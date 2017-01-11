package dvoraka.avservice.server.runner;

import dvoraka.avservice.common.runner.AbstractAppRunner;
import dvoraka.avservice.common.runner.AppRunner;
import dvoraka.avservice.common.service.ApplicationManagement;
import dvoraka.avservice.server.checker.CheckApp;
import dvoraka.avservice.server.configuration.jms.JmsConfig;

import java.io.IOException;

/**
 * JMS check runner.
 */
public class JmsCheckRunner extends AbstractAppRunner {

    public static void main(String[] args) throws IOException {
        AppRunner runner = new JmsCheckRunner();
        runner.run();
    }

    @Override
    protected String[] profiles() {
        return new String[]{"jms", "jms-checker", "no-db"};
    }

    @Override
    protected Class<?>[] configClasses() {
        return new Class<?>[]{JmsConfig.class};
    }

    @Override
    protected Class<? extends ApplicationManagement> runClass() {
        return CheckApp.class;
    }
}
