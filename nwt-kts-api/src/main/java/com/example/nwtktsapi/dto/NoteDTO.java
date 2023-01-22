package com.example.nwtktsapi.dto;

import com.example.nwtktsapi.model.NoteType;

public class NoteDTO {
    private Long offenderId;
    private NoteType noteType;
    private String content;

    public NoteDTO() {

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

    @Override
    public String toString() {
        return "NoteDTO{" +
                "offenderId=" + offenderId +
                ", noteType=" + noteType +
                ", content='" + content + '\'' +
                '}';
    }
}
