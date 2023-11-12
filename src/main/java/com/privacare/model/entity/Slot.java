package com.privacare.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "slot")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Slot {

    @Id
    @GeneratedValue(generator = "uuid4")
    @GenericGenerator(
            name = "uuid4",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Type(type = "pg-uuid")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private User doctor;

    @Column(name = "starts_at")
    private LocalDateTime startsAt;

    @Column(name = "reserved")
    private Boolean reserved;

    @OneToOne(mappedBy = "slot")
    private Appointment appointment;
}



