package dvoraka.avservice;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * AMQP AV server.
 */
public class AmqpAVServer extends AbstractAVServer implements AVServer {

    @Autowired
    private MessageProcessor messageProcessor;

    @Autowired
    private ListeningStrategy listeningStrategy;

    private static final Logger log = LogManager.getLogger(AmqpAVServer.class.getName());

    private ExecutorService executorService;


    public AmqpAVServer() {
        executorService = Executors.newFixedThreadPool(2);
    }


    public static void main(String[] args) {

        System.out.println("AMQP server");

        AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        AmqpAVServer server = context.getBean(AmqpAVServer.class);

        server.startListening();
        server.startResponding();

        System.out.println("After start.");
        try {
            Thread.sleep(15_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        server.stop();
        context.close();
    }

    private void startListening() {
        Runnable listening = this::listen;
        executorService.execute(listening);
    }

    private void startResponding() {
        Runnable responding = this::response;
        executorService.execute(responding);
    }

    private void listen() {
        log.debug("Starting listening...");
        listeningStrategy.listen();
    }

    private void response() {
        log.debug("Starting responding...");
        while (true) {
            if (isStopped()) {
                break;
            }

            if (messageProcessor.hasProcessedMessage()) {
                log.debug("Processed message: " + messageProcessor.getProcessedMessage());
            }
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void start() {
        log.debug("Server start.");
        setStarted();
    }

    @Override
    public void stop() {
        log.debug("Server stop.");
        setStopped();

        listeningStrategy.stop();
        messageProcessor.stop();

        executorService.shutdown();
        try {
            executorService.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void restart() {

    }
}
