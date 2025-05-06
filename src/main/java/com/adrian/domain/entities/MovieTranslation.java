package com.adrian.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
    name = "movie_translation",
    uniqueConstraints = @UniqueConstraint(columnNames = {"movie_id", "language_code"})
)
public class MovieTranslation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "movie_id", nullable = false)
    private Long movieId;

    @NotBlank
    @Column(name = "language_code", length = 10, nullable = false)
    private String languageCode;

    @Column(name = "translated_title", length = 255)
    private String translatedTitle;

    @Column(name = "translated_description", columnDefinition = "TEXT")
    private String translatedDescription;
}
