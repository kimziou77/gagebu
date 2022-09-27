package com.payhere.gagebu.domain.user.service;

import static com.payhere.gagebu.domain.user.dto.UserConverter.*;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.payhere.gagebu.domain.user.dto.UserRequest.UserCreate;
import com.payhere.gagebu.domain.user.dto.UserRequest.UserLogin;
import com.payhere.gagebu.domain.user.dto.UserResponse.UserCreated;
import com.payhere.gagebu.domain.user.dto.UserResponse.UserLoginToken;
import com.payhere.gagebu.domain.user.exception.UserAlreadyExistException;
import com.payhere.gagebu.domain.user.exception.UserNotFoundException;
import com.payhere.gagebu.domain.user.repository.UserRepository;
import com.payhere.gagebu.security.jwt.JwtTokenGenerator;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final UserRepository userRepository;

    private final JwtTokenGenerator tokenGenerator;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserCreated signUp(UserCreate userCreate) {
        userRepository.findByEmail(userCreate.email())
            .ifPresent(user -> {
                throw new UserAlreadyExistException();
            });

        var savedUser = userRepository.save(createToUser(passwordEncoder, userCreate));
        return new UserCreated(savedUser.getId());
    }

    public UserLoginToken login(UserLogin user) {
        var findUser = userRepository.findByEmail(user.email())
            .orElseThrow(UserNotFoundException::new);

        findUser.validatePassword(passwordEncoder, user.password());

        var accessToken = tokenGenerator.createAccessToken(findUser.getId(), findUser.getUserRole().toString());
        return new UserLoginToken(accessToken);
    }
}
