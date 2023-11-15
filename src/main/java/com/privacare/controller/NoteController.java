package com.privacare.controller;

import com.privacare.model.dto.request.NoteEditRequestDTO;
import com.privacare.model.dto.request.NoteRequestDTO;
import com.privacare.model.dto.response.NoteResponseDTO;
import com.privacare.service.NoteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/notes")
public class NoteController {

    private final NoteService noteService;

    @GetMapping
    public ResponseEntity<List<NoteResponseDTO>> getNotesByPatientId(@RequestParam UUID patientId) {
        List<NoteResponseDTO> result = this.noteService.getNotesBy(patientId);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NoteResponseDTO> getNoteBy(@PathVariable UUID id) {
        NoteResponseDTO result = this.noteService.getNoteBy(id);
        return ResponseEntity.ok().body(result);
    }

    @PostMapping
    public ResponseEntity<NoteResponseDTO> addNote(@RequestBody @Valid NoteRequestDTO noteRequestDTO) {
        NoteResponseDTO result = this.noteService.addNote(noteRequestDTO);
        return ResponseEntity.ok().body(result);
    }

    @PutMapping
    public ResponseEntity<Integer> editNote(@RequestBody @Valid NoteEditRequestDTO noteEditRequestDTO) {
        Integer result = this.noteService.editNote(noteEditRequestDTO);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/{noteId}")
    public ResponseEntity<Object> deleteNote(@PathVariable UUID noteId) {
        this.noteService.deleteNote(noteId);
        return ResponseEntity.noContent().build();
    }
}