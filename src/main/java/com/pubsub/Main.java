package com.pubsub;

import com.pubsub.entities.Message;
import com.pubsub.entities.Topic;
import com.pubsub.handlers.QueueManager;
import com.pubsub.impl.SimpleSubscriber;
import com.pubsub.impl.SleepingSubscriber;

public class Main {

    public static void main(String[] args) {

        QueueManager manager = QueueManager.getInstance();

        Topic topic1 = manager.addTopic("topic1");
        Topic topic2 = manager.addTopic("topic2");
        Topic topic3 = manager.addTopic("topic3");

        SimpleSubscriber subscriber1 = new SimpleSubscriber("subscriber 1");
        SimpleSubscriber subscriber2 = new SimpleSubscriber("subscriber 2");
        SimpleSubscriber subscriber3 = new SimpleSubscriber("subscriber 3");
        SleepingSubscriber subscriber4 = new SleepingSubscriber("subscriber 4", 5000);

        manager.addSubscriber(topic1, subscriber1);
        manager.addSubscriber(topic1, subscriber2);

        manager.addSubscriber(topic2, subscriber2);
        manager.addSubscriber(topic2, subscriber3);
        manager.addSubscriber(topic2, subscriber4);

        manager.addSubscriber(topic3, subscriber1);
        manager.addSubscriber(topic3, subscriber2);
        manager.addSubscriber(topic3, subscriber3);

        manager.publish(topic1, new Message("message 1"));
        manager.publish(topic2, new Message("message 2"));
        manager.publish(topic3, new Message("message 3"));
        manager.publish(topic1, new Message("message 1'"));

    }
}
