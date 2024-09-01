package com.pubsub.impl;

import com.pubsub.entities.Message;
import com.pubsub.interfaces.ISubscriber;

public class SleepingSubscriber implements ISubscriber {
    private String id;
    private long sleepTimeMillis;

    public SleepingSubscriber(String id, long sleepTimeMillis) {
        this.id = id;
        this.sleepTimeMillis = sleepTimeMillis;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void consume(Message message, int offset) {
        System.out.println("Subscriber : " + id + " started consuming message : " + message.getContent());
        try {
            Thread.sleep(sleepTimeMillis);
        } catch (InterruptedException e) {
          System.out.println("error, thread interrupted !");
          return;
        }
        System.out.println("Subscriber : " + id + " finished consuming message : " + message);
    }
}
