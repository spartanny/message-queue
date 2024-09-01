package com.pubsub.handlers;

import com.pubsub.entities.Message;
import com.pubsub.interfaces.ISubscriber;
import lombok.Getter;

import java.util.concurrent.atomic.AtomicInteger;

@Getter
public class TopicSubscriber {

    private AtomicInteger offset;
    private ISubscriber subscriber;

    public TopicSubscriber(ISubscriber subscriber) {
        this.offset = new AtomicInteger(0);
        this.subscriber = subscriber;
    }

    public void consume(Message message, int offset) {
        subscriber.consume(message, offset);
    }

}
