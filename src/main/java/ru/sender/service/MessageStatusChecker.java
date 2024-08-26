package ru.sender.service;

import ru.sender.utils.MessageStatus;

import java.util.List;
import java.util.Optional;

public interface MessageStatusChecker {

    /**
     * Получить статус сообщения по его идентификатору
     *
     * @param id Идентификатор сообщения
     * @return Опционально возвращает сообщение, если оно найдено
     */
    Optional<MessageStatus> getMessageStatus(Long id);

    /**
     * Получает историю состояний сообщения по его идентификатору
     *
     * @param id Идентификатор сообщения
     * @return Опционально возвращает список состояний сообщения, если оно найдено
     */
    Optional<List<MessageStatus>> getMessageHistory(Long id);
}
