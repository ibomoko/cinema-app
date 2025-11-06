package com.me.cinemaapp.controller;

import com.me.cinemaapp.model.response.HallResponse;
import com.me.cinemaapp.service.HallService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/halls")
public class HallController {

    private final HallService hallService;

    @GetMapping("/{id}")
    public HallResponse getHall(@PathVariable String id) {
        return hallService.getHallById(id);
    }
}
