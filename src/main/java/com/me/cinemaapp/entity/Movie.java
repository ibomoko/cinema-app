package com.me.cinemaapp.entity;

import com.me.cinemaapp.entity.idgenerator.CustomUUID;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "movies")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@DynamicUpdate
public class Movie {
    @Id
    @CustomUUID
    private String id;
    private String title;
    private String description;
    private String genre;
    private Integer duration;

    @Column(name = "release_year")
    private Integer releaseYear;
}
