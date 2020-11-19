package learning.microservice.note.feign.fallback;

import feign.hystrix.FallbackFactory;
import learning.microservice.note.DTOs.CallBackMailDto;
import learning.microservice.note.DTOs.MessageDTO;
import learning.microservice.note.feign.mail.FeignMail;
import org.springframework.stereotype.Component;

@Component
public class MailFallback implements FallbackFactory<FeignMail> {

    @Override
    public FeignMail create(Throwable throwable) {
        return new FeignMail() {
            @Override
            public CallBackMailDto sendMail(MessageDTO message) {
                return new CallBackMailDto("Service unavaible");
            }
        };
    }
}
