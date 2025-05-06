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
    name = "food_item_translation",
    uniqueConstraints = @UniqueConstraint(columnNames = {"food_item_id", "language_code"})
)
public class FoodItemTranslation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "food_item_id", nullable = false)
    private Long foodItemId;

    @NotBlank
    @Column(name = "language_code", length = 10, nullable = false)
    private String languageCode;

    @NotBlank
    @Column(name = "translated_name", length = 255, nullable = false)
    private String translatedName;
}