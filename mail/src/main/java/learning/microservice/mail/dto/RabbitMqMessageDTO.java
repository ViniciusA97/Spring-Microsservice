package learning.microservice.mail.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class RabbitMqMessageDTO {

    private String to;

    private String subject;

    private String message;

}
