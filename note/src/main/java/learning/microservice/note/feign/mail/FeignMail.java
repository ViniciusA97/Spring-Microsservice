package learning.microservice.note.feign.mail;


import learning.microservice.note.DTOs.CallBackMailDto;
import learning.microservice.note.DTOs.MessageDTO;
import learning.microservice.note.feign.fallback.MailFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value="mail", fallbackFactory = MailFallback.class)
public interface FeignMail {

    @RequestMapping(method = RequestMethod.POST , value="/v1")
    CallBackMailDto sendMail(@RequestBody MessageDTO message);


}
