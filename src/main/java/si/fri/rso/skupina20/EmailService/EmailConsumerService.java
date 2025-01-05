package si.fri.rso.skupina20.EmailService;

public class EmailConsumerService {
    private KafkaEmailConsumer kafkaEmailConsumer;

    public EmailConsumerService(KafkaEmailConsumer kafkaEmailConsumer) {
        this.kafkaEmailConsumer = kafkaEmailConsumer;
    }

    public void startConsumer() {
        System.out.println("Starting email consumer");
        new Thread(kafkaEmailConsumer::consumeEmails).start();
    }
}
