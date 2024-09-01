package com.pubsub.entities;

import com.pubsub.handlers.TopicSubscriber;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Topic {
    @Getter
    private String topicId;
    @Getter
    private Integer offset;
    List<Message> messageList;
    List<TopicSubscriber> subscribers;

    public Topic(String topicId) {
        this.topicId = topicId;
        this.offset = 0;
        this.messageList = new ArrayList<>();
        this.subscribers = new ArrayList<>();
    }

    public List<Message> getMessageList() {
        return Collections.unmodifiableList(messageList);
    }

    public List<TopicSubscriber> getSubscribers() {
        return Collections.unmodifiableList(subscribers);
    }

    public void addMessage(Message message) {
        messageList.add(message);
        ++offset;
//        notifySubscribers(message);
    }

    public void addSubscriber(TopicSubscriber subscriber) {
        subscribers.add(subscriber);
    }

    public boolean removeSubscriber(TopicSubscriber subscriber) {
        boolean success = false;
        if(!subscribers.isEmpty()) {
            success = subscribers.remove(subscriber);
        }
        return success;
    }

    private void notifySubscribers(Message message) {
        for(TopicSubscriber topicSubscriber : subscribers) {
            topicSubscriber.consume(message, offset);
        }
    }

}
