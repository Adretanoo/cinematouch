package com.adrian.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "session")
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "movie_id", nullable = false)
    private Long movieId;

    @NotNull
    @Column(name = "start_time", nullable = false)
    private Timestamp startTime;

    @NotNull
    @Column(name = "hall_number", nullable = false)
    private Integer hallNumber;
}
