package com.me.cinemaapp.controller;

import com.me.cinemaapp.model.response.UserDetailResponse;
import com.me.cinemaapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public UserDetailResponse me() {
        return userService.getRequestUserDetails();
    }
}
