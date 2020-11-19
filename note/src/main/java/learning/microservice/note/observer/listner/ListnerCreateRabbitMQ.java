package learning.microservice.note.observer.listner;


import learning.microservice.note.observer.abstracts.IEventRabbitmMQ;
import learning.microservice.note.observer.abstracts.IListnerRabbitMQ;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ListnerCreateRabbitMQ implements IListnerRabbitMQ {

    private RabbitTemplate rabbitTemplate;

    public ListnerCreateRabbitMQ(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void handleAction(IEventRabbitmMQ eventRabbitmMQ) {
        log.info("Sending message to create-queue msg: {}", eventRabbitmMQ.getJson());
            this.rabbitTemplate.convertAndSend("","mail-queue-create",eventRabbitmMQ.getJson());
    }

}
