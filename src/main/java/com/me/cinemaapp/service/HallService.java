package com.me.cinemaapp.service;

import com.me.cinemaapp.dao.HallRepository;
import com.me.cinemaapp.entity.Hall;
import com.me.cinemaapp.error.exception.ResourceNotFoundException;
import com.me.cinemaapp.model.response.HallResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HallService {

    private final HallRepository hallRepository;

    public HallResponse getHallById(String id) {
        Hall hall = hallRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hall not found with this id: " + id));

        return new HallResponse(hall.getName(), hall.getCapacity());
    }
}
