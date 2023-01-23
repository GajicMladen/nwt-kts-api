package com.example.nwtktsapi.controller;

import com.example.nwtktsapi.dto.NoteDTO;
import com.example.nwtktsapi.model.Note;
import com.example.nwtktsapi.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping(value = "api/note/")
public class NoteController {

    @Autowired
    private NoteService noteService;

    @PostMapping("save")
    public ResponseEntity<?> saveNote(@RequestBody NoteDTO noteDTO, Principal principal) {
        noteService.save(noteDTO, principal.getName());
        return ResponseEntity.ok().build();
    }

    @PostMapping("update")
    public ResponseEntity<?> updateNote (@RequestBody NoteDTO noteDTO, Principal principal) {
        noteService.update(noteDTO, principal.getName());
        return ResponseEntity.ok().build();
    }

    @GetMapping("get/block")
    public ResponseEntity<?> getBlockNoticeByOffenderId(@RequestParam Long id) {
        Note note = noteService.getBlockNotice(id);
        if (note != null) {
            return new ResponseEntity<>(new NoteDTO(note), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
