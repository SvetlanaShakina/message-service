package ru.sender.model;

import lombok.Data;
import ru.sender.utils.MessageStatus;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "message")
@Data
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    private String recipient;

    @Enumerated(EnumType.STRING)
    private MessageStatus status;

    private LocalDateTime createdAt;

    @ElementCollection(fetch = FetchType.LAZY)
    private List<MessageStatus> statusHistory = new ArrayList<>();
}

