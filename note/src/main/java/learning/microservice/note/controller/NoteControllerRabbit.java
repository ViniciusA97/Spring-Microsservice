package learning.microservice.note.controller;

import learning.microservice.core.model.note.Note;
import learning.microservice.core.model.user.User;
import learning.microservice.note.DTOs.NoteDTO;
import learning.microservice.note.observer.abstracts.AbstractObserver;
import learning.microservice.note.service.NoteServiceRabbit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("v1/notes/rabbit")
@Slf4j
public class NoteControllerRabbit {

    private AbstractObserver observer;


    @Autowired
    public NoteServiceRabbit noteServiceRabbit;

    @PostMapping()
    public ResponseEntity<?> postNote(@RequestBody NoteDTO dto, Principal principal){
        User user = (User) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        Note note = this.noteServiceRabbit.create(dto, user);
        if(note == null){
            return ResponseEntity.status(400).body("Erro ao criar um note.");
        }

        return ResponseEntity.ok().body(note);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateNote(@RequestBody NoteDTO dto,@PathVariable long id, Principal principal){
        User user = (User) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        Note note = this.noteServiceRabbit.update(dto, user, id);
        if(note == null){
            return ResponseEntity.status(400).body("Erro ao fazer o update.");
        }

        return ResponseEntity.ok().body(note);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteNote(@PathVariable long id, Principal principal){
        User user = (User) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        boolean bool = this.noteServiceRabbit.delete(user, id);
        if(bool){
            return ResponseEntity.ok().body("");
        }
        return ResponseEntity.status(400).body("Erro ao fazer o delete.");
    }


    @GetMapping
    public ResponseEntity<?> getUser(Principal principal){
        User user = (User) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        return new ResponseEntity<>(user, HttpStatus.OK);
    }



}
