package com.pubsub.interfaces;

import com.pubsub.entities.Message;

public interface ISubscriber {

    String getId();
    void consume(Message message, int offset);
}
