package com.example.UberProject_AuthService.repositories;

import com.example.UberProject_AuthService.models.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PassengerRepository extends JpaRepository<Passenger, Long> {


    Optional<Passenger> findPassengerByEmail(String email);
}
