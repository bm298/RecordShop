package com.NC_RecordShop.RecordShop.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
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
    private Integer releaseYear;
    private Integer stock;
    private Genre genre;
}

