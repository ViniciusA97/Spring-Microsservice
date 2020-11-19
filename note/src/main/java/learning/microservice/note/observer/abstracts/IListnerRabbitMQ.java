package learning.microservice.note.observer.abstracts;

public interface IListnerRabbitMQ {

    void handleAction(IEventRabbitmMQ eventRabbitmMQ);

}
