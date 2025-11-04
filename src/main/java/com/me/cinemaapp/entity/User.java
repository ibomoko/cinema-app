package com.me.cinemaapp.entity;

import com.me.cinemaapp.entity.idgenerator.CustomUUID;
import com.me.cinemaapp.enums.Role;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class User {
    @Id
    @CustomUUID
    private String id;
    private String username;
    private String name;
    private String surname;
    private String password;
    private String patronymic;
    private BigDecimal balance;

    @Enumerated(EnumType.STRING)
    private Role role;

}
