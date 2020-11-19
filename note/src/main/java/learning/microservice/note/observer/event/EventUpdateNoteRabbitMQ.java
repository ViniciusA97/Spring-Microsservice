package learning.microservice.note.observer.event;

import learning.microservice.note.observer.abstracts.IEventRabbitmMQ;

public class EventUpdateNoteRabbitMQ extends IEventRabbitmMQ {

    public EventUpdateNoteRabbitMQ(String json){
        this.json = json;
    }
}
