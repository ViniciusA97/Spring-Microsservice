package learning.microservice.note.observer.event;

import learning.microservice.note.observer.abstracts.IEventRabbitmMQ;

public class EventCreateNoteRabbitMQ extends IEventRabbitmMQ {

    public EventCreateNoteRabbitMQ(String json) {
        this.json = json;
    }

}
