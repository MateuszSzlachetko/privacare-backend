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
@Table(name = "task")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {

    @Id
    @GeneratedValue(generator = "uuid4")
    @GenericGenerator(
            name = "uuid4",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Type(type = "pg-uuid")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "creator_id", nullable = false)
    private User creator;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column
    private String content;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Column
    private String state;
}


