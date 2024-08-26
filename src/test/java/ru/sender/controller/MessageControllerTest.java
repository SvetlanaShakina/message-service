package ru.sender.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.sender.model.Message;
import ru.sender.service.MessageSender;
import ru.sender.service.MessageStatusChecker;
import ru.sender.utils.MessageStatus;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

public class MessageControllerTest {

    @Mock
    private MessageSender messageSender;

    @Mock
    private MessageStatusChecker messageStatusChecker;

    @InjectMocks
    private MessageController messageController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateMessage() {
        Message message = new Message();
        message.setContent("Test content");
        message.setRecipient("recipient@example.com");

        ResponseEntity<Message> response = messageController.createMessage(message);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testGetMessageStatus() {
        when(messageStatusChecker.getMessageStatus(anyLong())).thenReturn(Optional.of(MessageStatus.SENT));

        ResponseEntity<MessageStatus> response = messageController.getMessageStatus(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(MessageStatus.SENT, response.getBody());
    }

    @Test
    public void testGetMessageStatus_NotFound() {
        when(messageStatusChecker.getMessageStatus(anyLong())).thenReturn(Optional.empty());

        ResponseEntity<MessageStatus> response = messageController.getMessageStatus(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}

