package com.me.cinemaapp.converter;

import com.me.cinemaapp.entity.User;
import com.me.cinemaapp.model.response.UserDetailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class UserDetailResponseConverter implements Function<User, UserDetailResponse> {
    @Override
    public UserDetailResponse apply(User user) {
        return UserDetailResponse.builder()
                .username(user.getUsername())
                .name(user.getName())
                .surname(user.getSurname())
                .patronymic(user.getPatronymic())
                .balance(user.getBalance())
                .build();
    }
}
