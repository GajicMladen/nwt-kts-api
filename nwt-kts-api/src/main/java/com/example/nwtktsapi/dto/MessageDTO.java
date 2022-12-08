package com.example.nwtktsapi.dto;

import com.example.nwtktsapi.model.Message;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link com.example.nwtktsapi.model.Message} entity
 */
public class MessageDTO implements Serializable {
    private  Long userId;
    private  String content;
    private  boolean isAdminMessage;
    private  LocalDateTime timeStamp;

    public MessageDTO(){

    }

    public MessageDTO(Long userId, String content, boolean isAdminMessage, LocalDateTime timeStamp) {
        this.userId = userId;
        this.content = content;
        this.isAdminMessage = isAdminMessage;
        this.timeStamp = timeStamp;
    }

    public MessageDTO(Message message){
        this.userId = message.getUser().getId();
        this.content = message.getContent();
        this.isAdminMessage = message.isAdminMessage();
        this.timeStamp = message.getTimeStamp();
    }

    public Long getUserId() {
        return userId;
    }

    public String getContent() {
        return content;
    }

    public boolean getIsAdminMessage() {
        return isAdminMessage;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageDTO entity = (MessageDTO) o;
        return Objects.equals(this.userId, entity.userId) &&
                Objects.equals(this.content, entity.content) &&
                Objects.equals(this.isAdminMessage, entity.isAdminMessage) &&
                Objects.equals(this.timeStamp, entity.timeStamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, content, isAdminMessage, timeStamp);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "userId = " + userId + ", " +
                "content = " + content + ", " +
                "isAdminMessage = " + isAdminMessage + ", " +
                "timeStamp = " + timeStamp + ")";
    }
}