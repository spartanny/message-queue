package com.pubsub.handlers;

import com.pubsub.entities.Message;
import com.pubsub.entities.Topic;
import com.pubsub.runnables.SubscriberWorker;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public class TopicHandler {
    @Getter
    private Topic topic;
    private Map<String, SubscriberWorker> subscriberProcessor;

    public TopicHandler(Topic topic) {
        this.topic = topic;
        subscriberProcessor = new HashMap<>();
    }

    public void addSubscriber(TopicSubscriber subscriber) {
        topic.addSubscriber(subscriber);
    }

    public void publish(Message message) {
        topic.addMessage(message);
        for(TopicSubscriber topicSubscriber : topic.getSubscribers()) {
            startSubscriberWorker(topicSubscriber);
        }
    }

    private void startSubscriberWorker(TopicSubscriber topicSubscriber) {
        final String subscriberId = topicSubscriber.getSubscriber().getId();
        if(!subscriberProcessor.containsKey(subscriberId)) {
            SubscriberWorker subscriberWorker = new SubscriberWorker(
                this.topic, topicSubscriber
            );
            subscriberProcessor.put(subscriberId, subscriberWorker);
            // allow each subscriber message consumption to be run in parallel
            new Thread(subscriberWorker, subscriberWorker.getTopicSubscriber().getSubscriber().getId()).start();
        }
        SubscriberWorker subscriberWorker = subscriberProcessor.get(subscriberId);
        subscriberWorker.wake();
    }

}
