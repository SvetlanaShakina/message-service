package ru.sender.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sender.model.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {
}

