package com.privacare.controller;

import com.privacare.model.dto.request.NoteEditRequestDTO;
import com.privacare.model.dto.request.NoteRequestDTO;
import com.privacare.model.dto.response.NoteResponseDTO;
import com.privacare.service.NoteService;
import com.privacare.utilities.exception.ErrorDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/note")
public class NoteController {

    private final NoteService noteService;

    @GetMapping
    public ResponseEntity<List<NoteResponseDTO>> getNotesByPatientId(
            @RequestParam UUID patientId) {
        List<NoteResponseDTO> result = this.noteService.getNotesBy(patientId);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NoteResponseDTO> getNoteBy(
            @PathVariable UUID id) {
        NoteResponseDTO result = this.noteService.getNoteBy(id);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<NoteResponseDTO> addNote(@RequestBody @Valid NoteRequestDTO noteRequestDTO) {
        NoteResponseDTO result = this.noteService.addNote(noteRequestDTO);

        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Integer> editNote(@RequestBody @Valid NoteEditRequestDTO noteEditRequestDTO) {
        Integer result = this.noteService.editNote(noteEditRequestDTO);

        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @DeleteMapping("/{noteId}")
    public ResponseEntity<Object> deleteNote(@PathVariable UUID noteId) {
        try {
            this.noteService.deleteNote(noteId);
        } catch (EmptyResultDataAccessException e) {
            ErrorDetails errorDetails = ErrorDetails.builder()
                    .messages(List.of("Note with id: " + noteId + " not found"))
                    .timestamp(LocalDateTime.now())
                    .build();
            return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}