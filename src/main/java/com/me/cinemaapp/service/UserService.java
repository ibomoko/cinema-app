package com.me.cinemaapp.service;

import com.me.cinemaapp.converter.UserDetailResponseConverter;
import com.me.cinemaapp.entity.User;
import com.me.cinemaapp.model.UserInfo;
import com.me.cinemaapp.model.response.UserDetailResponse;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserDetailResponseConverter userDetailResponseConverter;

    @Resource(name = "requestScopedUser")
    private UserInfo userInfo;

    public UserDetailResponse getRequestUserDetails() {
        User currentUser = userInfo.getUser();
        return userDetailResponseConverter.apply(currentUser);
    }
}
