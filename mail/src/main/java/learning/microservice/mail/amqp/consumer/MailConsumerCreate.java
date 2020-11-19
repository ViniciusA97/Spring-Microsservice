package learning.microservice.mail.amqp.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import learning.microservice.mail.dto.RabbitMqMessageDTO;
import learning.microservice.mail.sender.MailService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MailConsumerCreate {

    @Autowired
    private MailService mailService;

    @Autowired
    private ObjectMapper mapper;

    @SneakyThrows
    @RabbitListener(queues = {"mail-queue-create"}, concurrency = "5")
    public void consumerQueueMailCreate(String msg){
        log.info("Email consumido. MSG: {}",msg);

        RabbitMqMessageDTO message = mapper.readValue(msg, RabbitMqMessageDTO.class);

        log.info(message.toString());

       this.mailService.sendMail(
                message.getTo(),
                message.getSubject(),
                message.getMessage()
        );
    }

}