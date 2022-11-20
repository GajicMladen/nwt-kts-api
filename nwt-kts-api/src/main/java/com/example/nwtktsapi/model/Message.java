package com.example.nwtktsapi.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sender_user_id",nullable = false)
    private User sender;

    @ManyToOne
    @JoinColumn(name = "offender_user_id")
    private Driver offender;

    @Column(name = "message_type")
    private MessageType messageType;

    @Column(name = "content")
    private String content;

    @Column(name = "date_created")
    private LocalDateTime dateCreated;

    public Message() {
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Driver getOffender() {
        return offender;
    }

    public void setOffender(Driver offender) {
        this.offender = offender;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
