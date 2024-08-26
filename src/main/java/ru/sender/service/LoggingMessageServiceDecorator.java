package ru.sender.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.sender.model.Message;
import ru.sender.repository.MessageRepository;
import ru.sender.utils.MessageStatus;

import java.util.List;
import java.util.Optional;

/**
 * Декоратор для сервиса отправки сообщений, добавляющий логирование и управление статусом сообщений
 */
@Service("loggingMessageServiceDecorator")
public class LoggingMessageServiceDecorator extends MessageServiceDecorator implements MessageStatusChecker {

    private final MessageRepository messageRepository;

    @Autowired
    public LoggingMessageServiceDecorator(@Qualifier("emailMessageService") MessageSender decoratedMessageSender,
                                          MessageRepository messageRepository) {
        super(decoratedMessageSender);
        this.messageRepository = messageRepository;
    }

    /**
     * Отправить сообщение, сохранив его в базе данных и добавив логирование
     *
     * @param message Сообщение, которое нужно отправить
     */
    @Override
    public void sendMessage(Message message) {
        updateStatus(message, MessageStatus.PENDING);
        try {
            super.sendMessage(message);
            updateStatus(message, MessageStatus.SENT);
        } catch (Exception e) {
            updateStatus(message, MessageStatus.ERROR);
        }
    }

    private void updateStatus(Message message, MessageStatus newStatus) {
        message.setStatus(newStatus);
        List<MessageStatus> history = message.getStatusHistory();
        history.add(newStatus);
        message.setStatusHistory(history);
        messageRepository.save(message);
    }

    /**
     * Получить статус сообщения по его идентификатору
     *
     * @param id Идентификатор сообщения
     * @return Опционально возвращает сообщение, если оно найдено
     */
    @Override
    public Optional<MessageStatus> getMessageStatus(Long id) {
        return messageRepository.findById(id)
                .map(Message::getStatus);
    }

    @Override
    public Optional<List<MessageStatus>> getMessageHistory(Long id) {
        return messageRepository.findById(id)
                .map(Message::getStatusHistory);
    }
}
