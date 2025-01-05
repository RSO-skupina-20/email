

package si.fri.rso.skupina20;

import si.fri.rso.skupina20.EmailService.EmailConsumerService;
import si.fri.rso.skupina20.EmailService.KafkaEmailConsumer;

import java.util.logging.Logger;


public class Main {
    private static Logger log = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        // Log the start of the application
        log.info("Starting the application");

        try {
            KafkaEmailConsumer kafkaEmailConsumer = new KafkaEmailConsumer();
            EmailConsumerService emailConsumerService = new EmailConsumerService(kafkaEmailConsumer);

            log.info("Started email consumer service");

            emailConsumerService.startConsumer();

        } catch (Exception e) {
            log.severe("Error initializing the application: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
