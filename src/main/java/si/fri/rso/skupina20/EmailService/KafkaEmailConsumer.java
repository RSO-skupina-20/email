package si.fri.rso.skupina20.EmailService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.MailerSendResponse;
import com.mailersend.sdk.emails.Email;
import com.mailersend.sdk.exceptions.MailerSendException;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import si.fri.rso.skupina20.DTO.EmailStructure;

import javax.enterprise.context.ApplicationScoped;
import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.logging.Logger;

@ApplicationScoped
public class KafkaEmailConsumer {

    static Logger log = Logger.getLogger(KafkaEmailConsumer.class.getName());

    private static String fromDomain = System.getenv("FROM_DOMAIN");
    private static String token = System.getenv("TOKEN");

    public void consumeEmails() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "host.docker.internal:9092");
        props.put("group.id", "email-processor-group");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        try (KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props)) {
            consumer.subscribe(Collections.singletonList("test-topic"));

            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
                for (ConsumerRecord<String, String> record : records) {
                    processEmail(record.value());
                }
            }
        }
    }

    private void processEmail(String emailMessage) {

        ObjectMapper objectMapper = new ObjectMapper();
        try{
            EmailStructure emailStructure = objectMapper.readValue(emailMessage, EmailStructure.class);
            sendEmail(emailStructure.getName(), emailStructure.getEmail(), emailStructure.getSubject(), emailStructure.getBody());

        } catch (Exception e) {
            log.info("Error parsing email message: " + e.getMessage());
        }
    }

    private static void sendEmail(String nameTo, String emailAddress, String subject, String text) {
        Email email = new Email();

        email.setFrom("Organizacija dogodkov", fromDomain);
        email.addRecipient(nameTo, emailAddress);

        email.setSubject(subject);
        email.setPlain(text);

        MailerSend ms = new MailerSend();
        ms.setToken(token);

        try {
            MailerSendResponse response = ms.emails().send(email);
            log.info("Email sent to: " + emailAddress + " with message id: " + response.messageId);
            System.out.println(response.messageId);
        } catch (MailerSendException e) {
            e.printStackTrace();
        }
    }
}
