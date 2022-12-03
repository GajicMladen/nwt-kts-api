package com.example.nwtktsapi.repository;

import com.example.nwtktsapi.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message,Long> {


    List<Message> findByUser_Id(Long id);

}
