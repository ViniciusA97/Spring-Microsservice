package learning.microservice.note.controller;

import learning.microservice.core.model.note.Note;
import learning.microservice.core.model.user.User;
import learning.microservice.note.DTOs.NoteDTO;
import learning.microservice.note.DTOs.response.ResponseFeignDto;
import learning.microservice.note.service.NoteServiceFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("v1/notes/feign")
@Slf4j
public class NoteControllerFeign {

    @Autowired
    private NoteServiceFeign noteServiceFeign;

    @PostMapping()
    public ResponseEntity<?> createUser(@RequestBody NoteDTO noteDTO, Principal principal){

        User user = (User) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        ResponseFeignDto responseFeignDto = this.noteServiceFeign.post(noteDTO,user);

        return new ResponseEntity<>(responseFeignDto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateNote(@RequestBody NoteDTO dto,@PathVariable long id, Principal principal){
        User user = (User) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        ResponseFeignDto note = this.noteServiceFeign.update(dto, user, id);
        if(note == null){
            return ResponseEntity.status(400).body("Erro ao fazer o update.");
        }

        return ResponseEntity.ok().body(note);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteNote(@PathVariable long id, Principal principal){
        User user = (User) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        boolean bool = this.noteServiceFeign.delete(user, id);
        if(!bool){
            return ResponseEntity.status(400).body("Erro ao fazer o update.");
        }

        return ResponseEntity.ok().body("");
    }



}
