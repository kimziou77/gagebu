package com.payhere.gagebu.domain.user.service;

import static com.payhere.gagebu.domain.user.dto.UserConverter.*;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.payhere.gagebu.domain.user.dto.UserRequest.UserCreate;
import com.payhere.gagebu.domain.user.dto.UserRequest.UserLogin;
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

    private final JavaMailSender javaMailSender;

    @Transactional
    public String signUp(UserCreate userCreate) {
        userRepository.findByEmail(userCreate.email())
            .ifPresent(user -> {
                throw new UserAlreadyExistException();
            });
        var savedUser = userRepository.save(createToUser(passwordEncoder, userCreate));
        return tokenGenerator.createEmailAuthToken(savedUser.getId(), userCreate.email());
    }

    public UserLoginToken login(UserLogin user) {
        var findUser = userRepository.findByEmail(user.email())
            .orElseThrow(UserNotFoundException::new);

        findUser.validatePassword(passwordEncoder, user.password());

        var accessToken = tokenGenerator.createAccessToken(findUser.getId(), findUser.getUserRole().toString());
        return new UserLoginToken(accessToken);
    }

    public void sendAuthEmail(String email, String url, String token) {
        var message = javaMailSender.createMimeMessage();
        var content = """
            <h1>회원가입 환영</h1>
            <br/>
            <div>
                <p>아래 링크를 클릭하시면 이메일 인증이 완료됩니다.</p>
                <a href="$url?token=$token">이메일 인증 확인 바로가기</a>
            </div>
            """.replace("$token", token).replace("$url", url);

        try {
            message.setSubject("[GAGEBU] 회원가입 이메일 인증", "utf-8");
            message.setText(content, "utf-8", "html");
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            javaMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @Transactional
    public Long verifyUser(String token) {
        var claims = tokenGenerator.getClaims(token);
        var userId = claims.get("userId", Long.class);
        var email = claims.get("email", String.class);
        var user = userRepository.findByEmail(email)
            .orElseThrow(UserNotFoundException::new);
        user.validateUserPermission(userId);
        user.verify();
        return userId;
    }
}
