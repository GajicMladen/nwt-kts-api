package com.example.nwtktsapi.model;

import com.example.nwtktsapi.dto.MessageDTO;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @Column(name = "content")
    private String content;

    @Column(name = "admin_message")
    private boolean isAdminMessage;


    @Column(name = "time_stamp")
    private LocalDateTime timeStamp;

    public Message(){

    }
    public Message(MessageDTO messageDTO,User user){
        this.content = messageDTO.getContent();
        this.isAdminMessage = messageDTO.getIsAdminMessage();
        this.timeStamp = messageDTO.getTimeStamp();
        this.user =  user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isAdminMessage() {
        return isAdminMessage;
    }

    public void setAdminMessage(boolean adminMessage) {
        isAdminMessage = adminMessage;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }
}
