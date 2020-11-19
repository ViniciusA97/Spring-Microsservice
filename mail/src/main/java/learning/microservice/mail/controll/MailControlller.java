package learning.microservice.mail.controll;

import learning.microservice.mail.dto.RabbitMqMessageDTO;
import learning.microservice.mail.dto.response.MessageDto;
import learning.microservice.mail.sender.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1")
public class MailControlller {

    @Autowired
    private MailService mailService;

    @PostMapping
    public ResponseEntity<?> sendMail(@RequestBody RabbitMqMessageDTO dto){
        this.mailService.sendMail(dto.getTo(), dto.getSubject(), dto.getMessage());

        MessageDto response = new MessageDto("Email enviado");

        return ResponseEntity.ok(response);
    }


}
