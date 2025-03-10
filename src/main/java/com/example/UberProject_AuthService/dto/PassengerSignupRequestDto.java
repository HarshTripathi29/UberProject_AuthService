package com.example.UberProject_AuthService.dto;

import lombok.*;

// why do we need getter setter for a dto.
// the dto would come in the form of a json then the serialization libraries would convert it in java object
// this java object would need the getter and setter to set the values and get the values.
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PassengerSignupRequestDto {
    // what details do we need for a passenger
    private String email;

    private String password;

    private String phoneNumber;

    private String name;
}
