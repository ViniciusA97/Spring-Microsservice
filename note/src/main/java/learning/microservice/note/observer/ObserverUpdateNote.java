package learning.microservice.note.observer;

import learning.microservice.note.observer.abstracts.AbstractObserver;
import learning.microservice.note.observer.abstracts.IEventRabbitmMQ;
import learning.microservice.note.observer.abstracts.IListnerRabbitMQ;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class ObserverUpdateNote extends AbstractObserver {

    @Override
    public void notifyEvent(IEventRabbitmMQ event) {
        for(IListnerRabbitMQ listner : this.listners ){
            listner.handleAction(event);
        }
    }

}
