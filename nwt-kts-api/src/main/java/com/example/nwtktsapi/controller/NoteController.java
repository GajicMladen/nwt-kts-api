package com.example.nwtktsapi.controller;

import com.example.nwtktsapi.dto.NoteDTO;
import com.example.nwtktsapi.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
