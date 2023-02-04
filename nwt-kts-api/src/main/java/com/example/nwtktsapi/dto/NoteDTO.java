package com.example.nwtktsapi.dto;

import com.example.nwtktsapi.model.Note;
import com.example.nwtktsapi.model.NoteType;

import java.time.LocalDateTime;

public class NoteDTO {
    private Long id;
    private Long senderId;
    private Long offenderId;
    private NoteType noteType;
    private String content;
    private LocalDateTime dateCreated;

    public NoteDTO() {

    }

    public NoteDTO(Note note) {
        this.id = note.getId();
        this.senderId = note.getSender().getId();
        this.offenderId = note.getOffender().getId();
        this.noteType = note.getNoteType();
        this.content = note.getContent();
        this.dateCreated = note.getDateCreated();
    }

    public Long getOffenderId() {
        return offenderId;
    }

    public void setOffenderId(Long offenderId) {
        this.offenderId = offenderId;
    }

    public NoteType getNoteType() {
        return noteType;
    }

    public void setNoteType(NoteType noteType) {
        this.noteType = noteType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Override
    public String toString() {
        return "NoteDTO{" +
                "offenderId=" + offenderId +
                ", noteType=" + noteType +
                ", content='" + content + '\'' +
                '}';
    }
}
