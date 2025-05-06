package com.adrian.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "movie")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title is required")
    @Column(nullable = false, length = 255)
    private String title;

    @Min(value = 1, message = "Duration must be at least 1 minute")
    @Column(nullable = false)
    private int duration;

    @Size(max = 100, message = "Genre must be at most 100 characters")
    @Column(length = 100)
    private String genre;

    @Size(max = 10, message = "Rating must be at most 10 characters")
    @Column(length = 10)
    private String rating;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "poster_image_url", columnDefinition = "TEXT")
    private String posterImageUrl;
}
