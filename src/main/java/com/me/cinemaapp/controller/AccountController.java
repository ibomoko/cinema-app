package com.me.cinemaapp.controller;


import com.me.cinemaapp.model.request.UserSignInRequest;
import com.me.cinemaapp.model.request.UserSignUpRequest;
import com.me.cinemaapp.model.security.TokenDetail;
import com.me.cinemaapp.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/public/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/sign-up")
    public HttpStatus signUp(@RequestBody @Valid UserSignUpRequest request) {
        accountService.signUp(request);
        return HttpStatus.OK;
    }

    @PostMapping("/sign-in")
    public TokenDetail signIn(@RequestBody @Valid UserSignInRequest request) {
        return accountService.signIn(request);
    }


}
