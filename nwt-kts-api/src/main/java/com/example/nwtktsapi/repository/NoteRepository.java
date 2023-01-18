package com.example.nwtktsapi.repository;

import com.example.nwtktsapi.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<Note, Long> {
}
