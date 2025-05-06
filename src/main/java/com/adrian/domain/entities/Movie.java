package com.adrian.domain.entities;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;
    private Integer duration;
    private String genre;
    private String rating;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "poster_image_url")
    private String posterImageUrl;
}
