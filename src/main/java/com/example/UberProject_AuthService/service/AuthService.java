package com.example.UberProject_AuthService.service;

import com.example.UberProject_AuthService.dto.PassengerDto;
import com.example.UberProject_AuthService.dto.PassengerSignupRequestDto;
import com.example.UberProject_AuthService.models.Passenger;
import com.example.UberProject_AuthService.repositories.PassengerRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {


    private final PassengerRepository passengerRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public AuthService(PassengerRepository passengerRepository, BCryptPasswordEncoder bCryptPasswordEncoder){
    this.passengerRepository = passengerRepository;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    // if we hit this service properly then we should be able to create a passenger with a plain password
    public PassengerDto signupPassenger(PassengerSignupRequestDto passengerSignupRequestDto){
    Passenger passenger = Passenger.builder()
            .email(passengerSignupRequestDto.getEmail())
            .name(passengerSignupRequestDto.getName())
            .password(bCryptPasswordEncoder.encode(passengerSignupRequestDto.getPassword()))
            .phoneNumber(passengerSignupRequestDto.getPhoneNumber())
            .build();

    // Your mistake was trying to call `getEmail()`, `getName()`, etc., on the class `PassengerSignupRequestDto` instead of its instance. Since these are **instance methods**, they require an object to be accessed. The correct way is to use `passengerSignupRequestDto.getEmail()`, not `PassengerSignupRequestDto.getEmail()`.
    Passenger newPassenger  = passengerRepository.save(passenger);
    return PassengerDto.from(newPassenger);
    }
    // from this passenger object we are going to create a dto object
}
