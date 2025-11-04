package com.me.cinemaapp.dao;

import com.me.cinemaapp.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatRepository extends JpaRepository<Seat, String> {

}
