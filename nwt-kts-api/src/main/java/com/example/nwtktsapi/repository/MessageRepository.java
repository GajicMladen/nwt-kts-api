package com.example.nwtktsapi.repository;

import com.example.nwtktsapi.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

public interface MessageRepository extends JpaRepository<Message,Long> {


    List<Message> findByUser_Id(Long id);

    @Query("SELECT m FROM Message m\n" +
            "WHERE m.id IN (\n" +
            "    SELECT MAX(m2.id) FROM Message m2\n" +
            "    GROUP BY m2.user\n" +
            ")")
    List<Message> getLastMessages();
}
