package com.example.nwtktsapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "note_id", nullable = false)
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "sender_user_id",nullable = false)
    private User sender;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "offender_user_id")
    //private Driver offender;
    private User offender;

    @Column(name = "note_type")
    private NoteType noteType;

    @Column(name = "content")
    private String content;

    @Column(name = "date_created")
    private LocalDateTime dateCreated;

    public Note() {
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

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public User getOffender() {
        return offender;
    }

    public void setOffender(User offender) {
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
