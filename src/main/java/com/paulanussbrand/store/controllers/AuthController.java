package com.paulanussbrand.store.controllers;

import com.paulanussbrand.store.dto.LoginRequest;
import com.paulanussbrand.store.dto.ResponseApi;
import com.paulanussbrand.store.dto.TokenToVerify;
import com.paulanussbrand.store.entities.User;
import com.paulanussbrand.store.service.AuthService;
import com.paulanussbrand.store.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<ResponseApi> login(@RequestBody LoginRequest loginRequest){
        String token = authService.validateLogin(loginRequest);
        return ResponseEntity.ok(new ResponseApi(
                200,
                List.of(token),
                "Okok",
                LocalDateTime.now()
        ));
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyToken(@RequestBody TokenToVerify tokenRequest){
        System.out.println(tokenRequest.token());
        Integer status = HttpStatus.UNAUTHORIZED.value();
        String message = "Inválido";

        String subject = tokenService.validateToken(tokenRequest.token());

        if (!subject.equals("")){
            status = HttpStatus.OK.value();
            message= "Válidado";
        }

        return ResponseEntity.status(status).body(new ResponseApi(
                status,
                Collections.emptyList(),
                message,
                LocalDateTime.now()
        ));
    }

}
