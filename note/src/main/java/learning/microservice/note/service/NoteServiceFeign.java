package learning.microservice.note.service;

import learning.microservice.core.model.note.Note;
import learning.microservice.core.model.user.User;
import learning.microservice.core.repository.note.NoteRepository;
import learning.microservice.note.DTOs.CallBackMailDto;
import learning.microservice.note.DTOs.MessageDTO;
import learning.microservice.note.DTOs.NoteDTO;
import learning.microservice.note.DTOs.response.ResponseFeignDto;
import learning.microservice.note.feign.mail.FeignMail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class NoteServiceFeign {

    @Autowired
    private FeignMail feignMail;

    @Autowired
    private NoteRepository repository;

    public ResponseFeignDto post(NoteDTO dto, User user){

        Note note = new Note(dto.getTitle(), dto.getText());
        this.repository.save(note);

        CallBackMailDto response = this.feignMail.sendMail(new MessageDTO(user.getEmail() ,"Create Note by Feign", "A new Note created by Feign Controller"));

        ResponseFeignDto feignDto = new ResponseFeignDto(response,note);

        return feignDto;

    }

    public ResponseFeignDto update(NoteDTO dto , User user ,long id){


        Optional<Note> note = this.repository.findById(id);

        if(!note.isPresent()){
            return  null;
        }

        Note noteContcret = note.get();

        noteContcret.setNote(dto.getText());
        noteContcret.setTitle(dto.getTitle());

        this.repository.save(noteContcret);

        CallBackMailDto response = this.feignMail.sendMail(new MessageDTO(user.getEmail() ,"Updated Note by Feign", "Note Updated by Feign Controller"));

        ResponseFeignDto feignDto = new ResponseFeignDto(response,noteContcret);

        return feignDto;

    }

    public boolean delete( User user ,long id){


        Optional<Note> note = this.repository.findById(id);

        if(!note.isPresent()){
            return false;
        }

        Note noteContcret = note.get();
        this.repository.delete(noteContcret);

        log.info("Note Deletd");

        CallBackMailDto response = this.feignMail.sendMail(new MessageDTO(user.getEmail() ,"Deleted Note by Feign", "Note Deleted by Feign Controller"));

        return true;
    }


}
