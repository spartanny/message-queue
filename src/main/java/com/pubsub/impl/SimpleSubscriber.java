package com.pubsub.impl;

import com.pubsub.entities.Message;
import com.pubsub.interfaces.ISubscriber;
public class SimpleSubscriber implements ISubscriber {

    private String id;

    public SimpleSubscriber(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void consume(Message message, int offset) {
        System.out.println("Subscriber : " + id + " started consuming message : " + message.getContent());
    }
}
