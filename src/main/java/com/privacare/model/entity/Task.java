package com.privacare.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "task")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {

    @Id
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "creator_id", nullable = false)
    private User creator;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column
    private String content;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Column
    private String state;
}


