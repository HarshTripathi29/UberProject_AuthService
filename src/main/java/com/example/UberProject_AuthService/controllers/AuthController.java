package com.example.UberProject_AuthService.controllers;

import com.example.UberProject_AuthService.dto.AuthRequestDto;
import com.example.UberProject_AuthService.dto.PassengerDto;
import com.example.UberProject_AuthService.dto.PassengerSignupRequestDto;
import com.example.UberProject_AuthService.service.AuthService;
import com.example.UberProject_AuthService.service.JwtService;
import org.apache.coyote.Response;
import org.springframework.security.core.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

// first signup and then signin separately
@RestController
@RequestMapping("/api/v1/auth")

public class AuthController {

    public final AuthenticationManager authenticationManager;

    private AuthService authService;

    private JwtService jwtService;

    public AuthController(AuthService authService, AuthenticationManager authencationManager, JwtService jwtService){
        this.authService=authService;
        this.authenticationManager=authencationManager;
        this.jwtService = jwtService;
    }

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Auth Service is working!");
    }


    @PostMapping("/signup/passenger")
    public ResponseEntity<PassengerDto> signUp(@RequestBody PassengerSignupRequestDto passengerSignupRequestDto){
        PassengerDto response = authService.signupPassenger(passengerSignupRequestDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    // send a resposne dto if the signup is done sucessfully.

    @PostMapping("/signin/passenger")
    public ResponseEntity<?> signIn(@RequestBody AuthRequestDto authRequestDto){

        System.out.println("token recieved "+authRequestDto.getEmail()+" "+authRequestDto.getPassword());
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDto.getEmail(), authRequestDto.getPassword()));
        if(authentication.isAuthenticated()){
            return new ResponseEntity<>("Successfull auth", HttpStatus.OK);
        }
        return new ResponseEntity<>("Auth not Possible",HttpStatus.OK);
    }
}
