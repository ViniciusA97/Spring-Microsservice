package learning.microservice.note.service;

import learning.microservice.core.model.note.Note;
import learning.microservice.core.model.user.User;
import learning.microservice.core.repository.note.NoteRepository;
import learning.microservice.note.DTOs.NoteDTO;
import learning.microservice.note.observer.ObserverControllNoteListner;
import learning.microservice.note.observer.event.EventCreateNoteRabbitMQ;
import learning.microservice.note.observer.event.EventDeleteNoteRabbitMQ;
import learning.microservice.note.observer.event.EventUpdateNoteRabbitMQ;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@Slf4j
public class NoteServiceRabbit {

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    public RabbitTemplate rabbitTemplate;


    public Note create(NoteDTO dto, User user){

        ObserverControllNoteListner observer = ObserverControllNoteListner.getInstance(this.rabbitTemplate);

        log.info("Init create Note");
            Note note = new Note(dto.getTitle(), dto.getText());
            this.noteRepository.save(note);
            log.info("created Note");

            log.info("building json");
            log.info("user: {}", user.toString());

            JSONObject json = new JSONObject();
            json.put("to",user.getEmail());
            json.put("subject","Create Note by Rabbit Controller");
            json.put("message","A Note has been created by Rabbit Controller");

            log.info("json builded: {}",json.toJSONString());

            EventCreateNoteRabbitMQ event = new EventCreateNoteRabbitMQ(json.toJSONString());

            log.info("created event");

            observer.handleEventCreate(event);

            log.info("submited to observer");
            return note;
    }

    public Note update(NoteDTO dto , User user ,long id){

        log.info("nOTE UPDATE");

        ObserverControllNoteListner observer = ObserverControllNoteListner.getInstance(this.rabbitTemplate);
        Optional<Note> note = noteRepository.findById(id);

        if(!note.isPresent()){
            return  null;
        }

        Note noteContcret = note.get();

        noteContcret.setNote(dto.getText());
        noteContcret.setTitle(dto.getTitle());

        noteRepository.save(noteContcret);

        log.info("Note updated");

        JSONObject json = new JSONObject();
        json.put("to",user.getEmail());
        json.put("subject","Updated Note by Rabbit Controller");
        json.put("message","A note has been updated by Rabbit Controller");

        EventUpdateNoteRabbitMQ event = new EventUpdateNoteRabbitMQ(json.toJSONString());

        observer.handleEventUpdate(event);

        log.info("Event submited.");

        return noteContcret;

    }

    public boolean delete( User user ,long id){

        log.info("nOTE UPDATE");

        ObserverControllNoteListner observer = ObserverControllNoteListner.getInstance(this.rabbitTemplate);
        Optional<Note> note = this.noteRepository.findById(id);

        if(!note.isPresent()){
            return false;
        }

        Note noteContcret = note.get();

        this.noteRepository.delete(noteContcret);

        log.info("Note Deletd");

        JSONObject json = new JSONObject();
        json.put("to",user.getEmail());
        json.put("subject","Deleted Note by Rabbit Controller");
        json.put("message","A note has been deleted by Rabbit Controller");

        EventDeleteNoteRabbitMQ event = new EventDeleteNoteRabbitMQ(json.toJSONString());

        observer.handleEventDelete(event);

        log.info("Event submited.");

        return true;
    }



}
