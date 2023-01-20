package com.example.nwtktsapi.service;

import com.example.nwtktsapi.dto.NoteDTO;
import com.example.nwtktsapi.model.Driver;
import com.example.nwtktsapi.model.Note;
import com.example.nwtktsapi.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class NoteService {

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private UserService userService;

    public void save(NoteDTO noteDTO, String senderEmail) {
        Note n = new Note();
        n.setSender(userService.findByEmail(senderEmail));
        n.setOffender(userService.getUserById(noteDTO.getOffenderId()));
        n.setContent(noteDTO.getContent());
        n.setNoteType(noteDTO.getNoteType());
        n.setDateCreated(LocalDateTime.now());

        noteRepository.save(n);
    }
}
