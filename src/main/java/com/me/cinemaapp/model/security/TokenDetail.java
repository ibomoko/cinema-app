package com.me.cinemaapp.model.security;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TokenDetail {
    private String token;
    private Long expirationDate;
    private Long createDate;
}
