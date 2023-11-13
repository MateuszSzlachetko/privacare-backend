package com.privacare.service;

import com.privacare.model.dto.request.NoteEditRequestDTO;
import com.privacare.model.dto.request.NoteRequestDTO;
import com.privacare.model.dto.response.NoteResponseDTO;
import com.privacare.model.entity.Note;
import com.privacare.model.entity.User;
import com.privacare.repository.NoteRepository;
import com.privacare.utilities.exception.custom.NoteNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class NoteService {

    private final UserService userService;

    private final NoteRepository noteRepository;

    public List<NoteResponseDTO> getNotesBy(UUID patientId) {
        User patient = this.userService.getUserBy(patientId);

        return this.noteRepository.findByPatientIdOrderByCreatedAtDesc(patient.getId()).stream()
                .map(NoteService::mapNoteToNoteResponse)
                .collect(Collectors.toList());
    }

    public NoteResponseDTO getNoteBy(UUID id) {
        return mapNoteToNoteResponse(this.noteRepository.findById(id).orElseThrow(
                () -> new NoteNotFoundException(id)));
    }

    public NoteResponseDTO addNote(NoteRequestDTO noteRequestDTO) {
        User creator = this.userService.getUserBy(noteRequestDTO.getCreatorId());
        User patient = this.userService.getUserBy(noteRequestDTO.getPatientId());

        Note note = Note.builder()
                .creator(creator)
                .patient(patient)
                .createdAt(LocalDateTime.now())
                .content(noteRequestDTO.getContent())
                .build();

        this.noteRepository.save(note);
        return mapNoteToNoteResponse(note);
    }

    public void deleteNote(UUID noteId) {
        try {
            this.noteRepository.deleteById(noteId);
        } catch (EmptyResultDataAccessException e) {
            throw new NoteNotFoundException(noteId);
        }
    }

    public Integer editNote(NoteEditRequestDTO noteEditRequestDTO) {
        this.noteRepository.findById(noteEditRequestDTO.getId()).orElseThrow(
                () -> new NoteNotFoundException(noteEditRequestDTO.getId()));

        return this.noteRepository.updateNoteBy(noteEditRequestDTO.getId(), noteEditRequestDTO.getContent());
    }

    private static NoteResponseDTO mapNoteToNoteResponse(Note note) {
        return NoteResponseDTO.builder()
                .id(note.getId())
                .creatorId(note.getCreator().getId())
                .patientId(note.getPatient().getId())
                .createdAt(note.getCreatedAt())
                .content(note.getContent())
                .build();
    }
}
