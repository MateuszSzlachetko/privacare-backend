package com.privacare.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "appointment")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {

    @Id
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "creator_id", nullable = false)
    private User creator;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private User patient;

    @Column(name = "starts_at")
    private Instant startsAt;

    @Column(name = "finishes_at")
    private Instant finishesAt;
}



