package com.payhere.gagebu.domain.user.service;

import static com.payhere.gagebu.domain.user.dto.UserConverter.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.payhere.gagebu.domain.user.dto.UserResponse.UserInfo;
import com.payhere.gagebu.domain.user.exception.UserNotFoundException;
import com.payhere.gagebu.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    public UserInfo getUserInfo(Long userId, Long loginId) {
        var user = userRepository.findById(userId)
            .orElseThrow(UserNotFoundException::new);
        user.validateUserPermission(loginId);
        return userToInfo(user);
    }
}
