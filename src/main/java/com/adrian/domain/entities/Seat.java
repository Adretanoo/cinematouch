package com.adrian.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
    name = "seat",
    uniqueConstraints = @UniqueConstraint(columnNames = {"hall_number", "row", "seat_number"})
)
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "hall_number", nullable = false)
    private Integer hallNumber;

    @NotNull
    @Column(name = "row", nullable = false, length = 1)
    private String row;

    @NotNull
    @Column(name = "seat_number", nullable = false)
    private Integer seatNumber;
}
