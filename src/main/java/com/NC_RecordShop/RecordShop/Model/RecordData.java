package com.NC_RecordShop.RecordShop.Model;

import jakarta.persistence.*;
//import jakarta.validation.constraints.Max;
//import jakarta.validation.constraints.Min;
//import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.NotNull;
import lombok.*;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter


public class RecordData {
    @Id
    @GeneratedValue
    private Long id;
    private String albumName;
    private String artist;

//    @NotBlank (message = "Stock cannot be empty")
//    @NotNull (message = "Stock cannot be null")
//    @Min(value = 0, message = "Stock must be at least 0")
//    @Max(value = 10000, message = "Stock must be less than or equal to 10,000")
    private Integer stock;

//    @NotBlank (message = "Release Year cannot be empty")
//    @NotNull(message = "Release year cannot be null")
//    @Min(value = 0, message = "Release year must be at least 0")
//    @Max(value = 2024, message = "Release year must be less than or equal to 2024")
    private Integer releaseYear;

    private Genre genre;
}

