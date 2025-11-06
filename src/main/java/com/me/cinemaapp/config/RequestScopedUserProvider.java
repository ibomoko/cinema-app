package com.me.cinemaapp.config;

import com.me.cinemaapp.dao.UserRepository;
import com.me.cinemaapp.entity.User;
import com.me.cinemaapp.error.exception.ResourceNotFoundException;
import com.me.cinemaapp.model.UserInfo;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.annotation.RequestScope;

@Configuration
@RequiredArgsConstructor
public class RequestScopedUserProvider {

    private final UserRepository userRepository;

    @RequestScope
    @Bean
    public UserInfo requestScopedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || StringUtils.isBlank(authentication.getName())) {
            return null;
        }

        String userId = authentication.getName();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        return new UserInfo(user);
    }


}
