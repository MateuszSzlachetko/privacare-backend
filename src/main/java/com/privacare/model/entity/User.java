package com.privacare.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "\"user\"")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(generator = "uuid4")
    @GenericGenerator(
            name = "uuid4",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Type(type = "pg-uuid")
    private UUID id;

    @Column
    private UUID authId;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column
    private String name;

    @Column
    private String surname;

    @Column
    private String pesel;

    @Column(name = "phone_number")
    private String phoneNumber;

    @OneToMany(mappedBy = "creator")
    private Set<Task> createdTasks;

    @OneToMany(mappedBy = "creator")
    private Set<News> createdNews;

    @OneToMany(mappedBy = "creator")
    private Set<Note> createdNotes;

    @OneToMany(mappedBy = "patient")
    private Set<Note> patientOfTheNotes;

    @OneToMany(mappedBy = "creator")
    private Set<Appointment> createdAppointments;

    @OneToMany(mappedBy = "patient")
    private Set<Appointment> patientOfTheAppointments;
}
