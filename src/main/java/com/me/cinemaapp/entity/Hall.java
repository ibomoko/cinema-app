package com.me.cinemaapp.entity;

import com.me.cinemaapp.entity.idgenerator.CustomUUID;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "halls")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Hall {
    @Id
    @CustomUUID
    private String id;
    private String name;
    private Integer capacity;
}
