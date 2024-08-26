package ru.sender.utils;

public enum MessageStatus {
    /**
     * Сообщение в очереди на отправку
     */
    PENDING,

    /**
     * Сообщение успешно отправлено
     */
    SENT,

    /**
     * Произошла ошибка при отправке сообщения
     */
    ERROR
}
