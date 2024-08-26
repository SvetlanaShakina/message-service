package ru.sender.service;


import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ru.sender.model.Message;
import ru.sender.utils.MessageStatus;

@Service
public class AsyncEmailMessageService implements MessageSender {

    @Async
    @Override
    public void sendMessage(Message message) {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        message.setStatus(MessageStatus.SENT);
    }
}
