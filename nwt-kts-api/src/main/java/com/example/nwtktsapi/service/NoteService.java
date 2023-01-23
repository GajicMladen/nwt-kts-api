package com.example.nwtktsapi.service;

import com.example.nwtktsapi.dto.NoteDTO;
import com.example.nwtktsapi.model.Note;
import com.example.nwtktsapi.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
        n.setDateCreated(noteDTO.getDateCreated());

        noteRepository.save(n);
    }

    public void update(NoteDTO noteDTO, String name) {
        Optional<Note> n = noteRepository.findById(noteDTO.getId());
        if (n.isPresent()) {
            Note note = n.get();
            note.setContent(note.getContent());
            noteRepository.save(note);
        }
    }

    public Note getBlockNotice(Long id) {
        List<Optional<Note>> notes = noteRepository.findByOffenderId(id);
        if (notes.size() != 0 && notes.get(0).isPresent()) {
            return notes.get(0).get();
        }
        return null;
    }
}
