package com.paulanussbrand.store.service;

import com.paulanussbrand.store.dto.LoginRequest;
import com.paulanussbrand.store.entities.User;
import com.paulanussbrand.store.exceptions.AuthException;
import com.paulanussbrand.store.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenService tokenService;

    public String validateLogin(LoginRequest loginRequest){
        Optional<User> userFromDataBase = userRepository.findByEmail(loginRequest.email());

        if (userFromDataBase.isEmpty()) throw new AuthException("Usuário Inválido!", HttpStatus.BAD_REQUEST);
        User user = userFromDataBase.get();
        if (!user.getPassword().equals(loginRequest.password())) throw new AuthException("Senha incorreta!", HttpStatus.BAD_REQUEST);

        return tokenService.generateToken(user);
    }
}
