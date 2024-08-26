package ru.sender.controller;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sender.model.Message;
import ru.sender.service.MessageSender;
import ru.sender.service.MessageStatusChecker;
import ru.sender.utils.MessageStatus;

import java.util.List;

/**
 * REST-контроллер для управления сообщениями
 */
@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageSender messageSender;
    private final MessageStatusChecker messageStatusChecker;

    public MessageController(@Qualifier("loggingMessageServiceDecorator") MessageSender messageSender,
                             @Qualifier("loggingMessageServiceDecorator") MessageStatusChecker messageStatusChecker) {
        this.messageSender = messageSender;
        this.messageStatusChecker = messageStatusChecker;
    }

    /**
     * Создать и отправить новое сообщение
     *
     * @param message Сообщение для отправки
     * @return Ответ с отправленным сообщением
     */
    @PostMapping
    public ResponseEntity<Message> createMessage(@RequestBody Message message) {
        messageSender.sendMessage(message);
        return ResponseEntity.ok(message);
    }

    /**
     * Получить статус сообщения по его идентификатору
     *
     * @param id Идентификатор сообщения
     * @return Ответ с сообщением, если оно найдено, или 404 Not Found
     */
    @GetMapping("/{id}/status")
    public ResponseEntity<MessageStatus> getMessageStatus(@PathVariable Long id) {
        return messageStatusChecker.getMessageStatus(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/history")
    public ResponseEntity<List<MessageStatus>> getMessageHistory(@PathVariable Long id) {
        return messageStatusChecker.getMessageHistory(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
