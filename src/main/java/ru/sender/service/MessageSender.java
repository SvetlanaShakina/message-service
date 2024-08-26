package ru.sender.service;

import ru.sender.model.Message;

import java.util.Optional;

/**
 * Интерфейс для сервисов, ответственных за отправку сообщений
 */
public interface MessageSender {
    /**
     * Отправить сообщение
     *
     * @param message Сообщение, которое нужно отправить.
     */
    void sendMessage(Message message);
}
