package learning.microservice.note.DTOs.response;

import learning.microservice.core.model.note.Note;
import learning.microservice.note.DTOs.CallBackMailDto;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class ResponseFeignDto {

    private CallBackMailDto mail;

    private Note note;
}
