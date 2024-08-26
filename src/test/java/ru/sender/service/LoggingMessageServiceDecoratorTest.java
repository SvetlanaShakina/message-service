package ru.sender.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.sender.model.Message;
import ru.sender.repository.MessageRepository;
import ru.sender.utils.MessageStatus;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class LoggingMessageServiceDecoratorTest {

    @Mock
    private MessageSender messageSender;

    @Mock
    private MessageRepository messageRepository;

    @InjectMocks
    private LoggingMessageServiceDecorator loggingMessageServiceDecorator;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSendMessage() {
        Message message = new Message();
        message.setId(1L);
        message.setContent("Test content");
        message.setRecipient("recipient@example.com");

        loggingMessageServiceDecorator.sendMessage(message);

        verify(messageRepository, times(2)).save(message);
        verify(messageSender, times(1)).sendMessage(message);
        assertEquals(MessageStatus.SENT, message.getStatus());
    }

    @Test
    public void testGetMessageStatus() {
        Message message = new Message();
        message.setId(1L);
        message.setStatus(MessageStatus.SENT);

        when(messageRepository.findById(anyLong())).thenReturn(Optional.of(message));

        Optional<MessageStatus> status = loggingMessageServiceDecorator.getMessageStatus(1L);

        assertEquals(Optional.of(MessageStatus.SENT), status);
    }

    @Test
    public void testGetMessageHistory() {
        Message message = new Message();
        message.setId(1L);
        message.getStatusHistory().add(MessageStatus.PENDING);
        message.getStatusHistory().add(MessageStatus.SENT);

        when(messageRepository.findById(anyLong())).thenReturn(Optional.of(message));

        Optional<List<MessageStatus>> history = loggingMessageServiceDecorator.getMessageHistory(1L);

        assertEquals(2, history.get().size());
        assertEquals(MessageStatus.PENDING, history.get().get(0));
        assertEquals(MessageStatus.SENT, history.get().get(1));
    }
}
