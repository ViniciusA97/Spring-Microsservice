package learning.microservice.note.observer.listner;

import learning.microservice.note.observer.abstracts.IEventRabbitmMQ;
import learning.microservice.note.observer.abstracts.IListnerRabbitMQ;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

@Slf4j
public class ListnerUpdateRabbitMQ implements IListnerRabbitMQ {

    public ListnerUpdateRabbitMQ(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    private RabbitTemplate rabbitTemplate;

    @Override
    public void handleAction(IEventRabbitmMQ eventRabbitmMQ) {
        log.info("Sending message to update-queue");
        this.rabbitTemplate.convertAndSend(
                "",
                "mail-queue-update",
                eventRabbitmMQ.getJson()
        );
    }

}
