package dvoraka.avservice.server.configuration.amqp;

import dvoraka.avservice.common.testing.DefaultPerformanceTestProperties;
import dvoraka.avservice.common.testing.PerformanceTestProperties;
import dvoraka.avservice.db.service.MessageInfoService;
import dvoraka.avservice.server.ServerComponent;
import dvoraka.avservice.server.amqp.AmqpComponent;
import dvoraka.avservice.server.checker.CheckApp;
import dvoraka.avservice.server.checker.Checker;
import dvoraka.avservice.server.checker.DefaultPerformanceTester;
import dvoraka.avservice.server.checker.SimpleChecker;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * AMQP checker configuration for import.
 */
@Configuration
@Profile("amqp-checker")
public class AmqpCheckerConfig {

    @Value("${avservice.amqp.resultQueue:av-result}")
    private String resultQueue;

    @Value("${avservice.amqp.checkExchange:check}")
    private String checkExchange;

    @Value("${avservice.serviceId:default1}")
    private String serviceId;


    @Bean
    public ServerComponent serverComponent(
            RabbitTemplate rabbitTemplate,
            MessageInfoService messageInfoService
    ) {
        return new AmqpComponent(checkExchange, serviceId, rabbitTemplate, messageInfoService);
    }

    @Bean
    public MessageListener messageListener(ServerComponent serverComponent) {
        return serverComponent;
    }

    @Bean
    public SimpleMessageListenerContainer messageListenerContainer(
            ConnectionFactory connectionFactory, MessageListener messageListener) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(resultQueue);
        container.setMessageListener(messageListener);

        return container;
    }

    @Bean
    public Checker checker(ServerComponent serverComponent) {
        return new SimpleChecker(serverComponent);
    }

    @Bean
    public PerformanceTestProperties testProperties() {
        return new DefaultPerformanceTestProperties();
    }

    @Bean
    public DefaultPerformanceTester defaultLoadTester(
            Checker checker,
            PerformanceTestProperties testProperties
    ) {
        return new DefaultPerformanceTester(checker, testProperties);
    }

    @Bean
    public CheckApp checkApp(Checker checker) {
        return new CheckApp(checker);
    }
}