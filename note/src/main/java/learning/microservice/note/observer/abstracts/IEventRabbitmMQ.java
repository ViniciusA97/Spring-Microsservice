package learning.microservice.note.observer.abstracts;

public abstract class IEventRabbitmMQ {

    protected String json;

    public String getJson(){
        return this.json;
    }
}
