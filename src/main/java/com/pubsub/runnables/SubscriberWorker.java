package com.pubsub.runnables;

import com.pubsub.entities.Message;
import com.pubsub.entities.Topic;
import com.pubsub.handlers.TopicSubscriber;
import lombok.Getter;
import lombok.SneakyThrows;


@Getter
public class SubscriberWorker implements Runnable {

    private final Topic topic;
    private final TopicSubscriber topicSubscriber;

    public SubscriberWorker(Topic topic, TopicSubscriber topicSubscriber) {
        this.topic = topic;
        this.topicSubscriber = topicSubscriber;
    }

    @Override
    @SneakyThrows
    public void run() {
        synchronized (topicSubscriber) {
            do {
                int currOffset = topicSubscriber.getOffset().get();
                while(currOffset >= topic.getOffset())
                    topicSubscriber.wait(); // Similar to thread sleep; caught up till last message wait until you get a notification to process new message

                Message message = topic.getMessageList().get(currOffset);
                topicSubscriber.consume(message, currOffset);

                // As the subscriber offset can be reset ; don't blindly increment
                topicSubscriber.getOffset().compareAndSet(currOffset, currOffset + 1);
            }
            while(true);
        }
    }

    public void wake() {
        synchronized (topicSubscriber) {
            topicSubscriber.notify();
        }
    }

}
