package learning.microservice.note.observer;

import learning.microservice.note.observer.event.EventCreateNoteRabbitMQ;
import learning.microservice.note.observer.event.EventDeleteNoteRabbitMQ;
import learning.microservice.note.observer.event.EventUpdateNoteRabbitMQ;
import learning.microservice.note.observer.listner.ListnerCreateRabbitMQ;
import learning.microservice.note.observer.listner.ListnerDeleteRabbitMQ;
import learning.microservice.note.observer.listner.ListnerUpdateRabbitMQ;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

public class ObserverControllNoteListner {

        private RabbitTemplate rabbitTemplate;

        private static ObserverControllNoteListner instance;

        private ObserverCreateNote observerCreateNote;

        private ObserverUpdateNote observerUpdateNote;

        private ObserverDeleteNote observerDeleteNote;

        private ObserverControllNoteListner(RabbitTemplate rabbitTemplate){
            this.observerCreateNote = new ObserverCreateNote();
            this.observerDeleteNote = new ObserverDeleteNote();
            this.observerUpdateNote = new ObserverUpdateNote();
            this.rabbitTemplate = rabbitTemplate;

            this.setInitialListners();
        }

        public static ObserverControllNoteListner getInstance(RabbitTemplate rabbitTemplate){
            if(instance==null) instance = new ObserverControllNoteListner(rabbitTemplate);
            return instance;
        }

        private void setInitialListners(){
            this.observerCreateNote.addListner(new ListnerCreateRabbitMQ(this.rabbitTemplate));
            this.observerUpdateNote.addListner(new ListnerUpdateRabbitMQ(this.rabbitTemplate));
            this.observerDeleteNote.addListner(new ListnerDeleteRabbitMQ(this.rabbitTemplate));
        }

        public void addListnerCreateNote(ListnerCreateRabbitMQ listnerCreateRabbitMQ){
            this.observerCreateNote.addListner(listnerCreateRabbitMQ);
        }

        public void addListnerUpdateNote(ListnerUpdateRabbitMQ listnerUpdateRabbitMQ){
            this.observerUpdateNote.addListner(listnerUpdateRabbitMQ);
        }

        public void addListnerDeleteNote(ListnerDeleteRabbitMQ listnerDeleteRabbitMQ){
            this.observerDeleteNote.addListner(listnerDeleteRabbitMQ);
        }

        public void handleEventCreate(EventCreateNoteRabbitMQ event){
            this.observerCreateNote.notifyEvent(event);
        }

        public void handleEventUpdate(EventUpdateNoteRabbitMQ event){
            this.observerUpdateNote.notifyEvent(event);
        }

        public void handleEventDelete(EventDeleteNoteRabbitMQ event){
            this.observerDeleteNote.notifyEvent(event);
        }

}
