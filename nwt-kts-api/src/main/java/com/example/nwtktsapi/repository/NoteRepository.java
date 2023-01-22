package com.example.nwtktsapi.repository;

import com.example.nwtktsapi.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface NoteRepository extends JpaRepository<Note, Long> {
    @Query("select n from Note n where n.offender.id = ?1 order by n.dateCreated desc")
    List<Optional<Note>> findByOffenderId(Long id);
}
