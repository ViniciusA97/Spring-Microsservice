package learning.microservice.mail.amqp.queue;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class AmqpQueue {

    @Bean
    public Queue mailQueueCreate() {
        return new Queue("mail-queue-create", true);
    }

    @Bean
    public Queue mailQueueUpdate() {
        return new Queue("mail-queue-update", true);
    }

    @Bean
    public Queue mailQueueDelete() {
        return new Queue("mail-queue-delete", true);
    }

}
