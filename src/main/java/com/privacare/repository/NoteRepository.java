package com.privacare.repository;

import com.privacare.model.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Repository
public interface NoteRepository extends JpaRepository<Note, UUID> {
    List<Note> findByPatientIdOrderByCreatedAtDesc(UUID patientId);

    @Transactional
    @Modifying
    @Query("update Note n set n.content = :content where n.id = :id")
    Integer updateNoteBy(@Param("id") UUID noteId, @Param("content") String content);
}