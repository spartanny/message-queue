package com.pubsub.handlers;

import com.pubsub.entities.Message;
import com.pubsub.entities.Topic;
import com.pubsub.interfaces.ISubscriber;

import java.util.HashMap;
import java.util.Map;

public class QueueManager {

    Map<String, Topic> topicMap;
    Map<String, TopicHandler> topicHandlerMap;
    private static QueueManager INSTANCE = null;

    private QueueManager() {
        topicMap = new HashMap<>();
        topicHandlerMap = new HashMap<>();
    }

    public static synchronized QueueManager getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new QueueManager();
        }
        return INSTANCE;
    }

    public Topic addTopic(String topicId) {
        if(!topicHandlerMap.containsKey(topicId)) {
            Topic topic = new Topic(topicId);
            topicHandlerMap.put(topicId, new TopicHandler(topic));
        }
        return topicHandlerMap.get(topicId).getTopic();
    }

    public void addSubscriber(Topic topic, ISubscriber subscriber) {
        topic.addSubscriber(new TopicSubscriber(subscriber));
        System.out.println(subscriber.getId() + " subscribed to topic: " + topic.getTopicId());
    }

    public void publish(Topic topic, Message message) {
        String topicId = topic.getTopicId();
        if(topicHandlerMap.containsKey(topicId)) {
            TopicHandler topicHandler = topicHandlerMap.get(topicId);
            topicHandler.publish(message);
        }
    }

    public void resetOffset(String topic, ISubscriber isubscriber, int offset) {

    }

}
