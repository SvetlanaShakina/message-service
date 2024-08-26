package ru.sender.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import ru.sender.model.Message;
import ru.sender.utils.MessageStatus;

/**
 * Реализация сервиса для отправки email-сообщений
 */
@Service("emailMessageService")
public class EmailMessageService implements MessageSender {

    @Autowired
    private JavaMailSender mailSender;

    /**
     * Отправить email-сообщение
     *
     * @param message Сообщение, которое нужно отправить
     */
    @Override
    public void sendMessage(Message message) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(message.getRecipient());
        email.setSubject("Новое cообщение");
        email.setText(message.getContent());

        try {
            mailSender.send(email);
            message.setStatus(MessageStatus.SENT);
            System.out.println("E-mail отправлен на " + message.getRecipient());
        } catch (Exception e) {
            message.setStatus(MessageStatus.ERROR);
            System.err.println("Ошибка при отправке e-mail на " + message.getRecipient() + ": " + e.getMessage());
        }
    }
}
