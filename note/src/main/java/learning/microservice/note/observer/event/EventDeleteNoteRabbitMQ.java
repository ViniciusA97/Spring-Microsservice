package learning.microservice.note.observer.event;

import learning.microservice.note.observer.abstracts.IEventRabbitmMQ;

public class EventDeleteNoteRabbitMQ extends IEventRabbitmMQ {

    public EventDeleteNoteRabbitMQ(String json) {
        this.json = json;
    }
}
