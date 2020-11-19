package learning.microservice.note.DTOs;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MessageDTO {

    private String to;

    private String subject;

    private String message;

}
