package learning.microservice.note.observer.listner;

import learning.microservice.note.observer.abstracts.IEventRabbitmMQ;
import learning.microservice.note.observer.abstracts.IListnerRabbitMQ;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

@Slf4j
public class ListnerDeleteRabbitMQ implements IListnerRabbitMQ {

    private  RabbitTemplate rabbitTemplate;

    public ListnerDeleteRabbitMQ(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void handleAction(IEventRabbitmMQ eventRabbitmMQ) {
        log.info("Sending message to delete-queue");
        this.rabbitTemplate.convertAndSend(
                "",
                "mail-queue-delete",
                eventRabbitmMQ.getJson()
        );
    }

}
