package com.example.UberProject_AuthService.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler" , "bookings"})
public class Passenger extends BaseModel {
    private String name;

    // @Column(nullable = false)
    private String phoneNumber;

    // @Column(nullable = false)
    private String email;

    // @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy="passenger")
    private List<Booking> bookings = new ArrayList<>();
}
