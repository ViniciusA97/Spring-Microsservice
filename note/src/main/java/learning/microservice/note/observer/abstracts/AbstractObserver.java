package learning.microservice.note.observer.abstracts;

import java.util.ArrayList;

public abstract class AbstractObserver {

    protected ArrayList<IListnerRabbitMQ> listners;

    public AbstractObserver(){
        this.listners = new ArrayList<IListnerRabbitMQ>();
    }

    public abstract void notifyEvent(IEventRabbitmMQ event);

    public void addListner(IListnerRabbitMQ listner){
        this.listners.add(listner);
    }

    public void deleteListner(IListnerRabbitMQ listner){
        this.listners.remove(listner);
    }

}
