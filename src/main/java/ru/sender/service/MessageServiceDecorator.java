package ru.sender.service;

import ru.sender.model.Message;

public abstract class MessageServiceDecorator implements MessageSender {

    protected MessageSender decoratedMessageSender;

    public MessageServiceDecorator(MessageSender decoratedMessageSender) {
        this.decoratedMessageSender = decoratedMessageSender;
    }

    @Override
    public void sendMessage(Message message) {
        decoratedMessageSender.sendMessage(message);
    }
}
