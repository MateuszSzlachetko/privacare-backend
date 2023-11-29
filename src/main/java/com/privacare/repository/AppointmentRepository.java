package com.privacare.repository;

import com.privacare.model.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, UUID> {
    List<Appointment> findByPatientIdOrderBySlotStartsAtDesc(UUID id);
    
    List<Appointment> findBySlotStartsAtBetween(LocalDateTime start, LocalDateTime end);

    List<Appointment> findBySlotStartsAtBetweenAndPatientId(LocalDateTime start, LocalDateTime end, UUID id);

}