package learning.microservice.mail.sender;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MailService{

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendMail(String to, String subject, String message){
        log.info("sending message");
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(to);
        mail.setFrom("LEARNING_MICROSSERVICE");
        mail.setSubject(subject);
        mail.setText(message);
        this.javaMailSender.send(mail);
    }


}
