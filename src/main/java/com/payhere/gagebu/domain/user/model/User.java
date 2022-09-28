package com.payhere.gagebu.domain.user.model;

import static com.google.common.base.Preconditions.*;
import static com.payhere.gagebu.common.exception.error.ErrorMessage.*;
import static lombok.AccessLevel.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.payhere.gagebu.common.model.BaseEntity;
import com.payhere.gagebu.domain.user.exception.UserNoPermissionException;

import io.jsonwebtoken.lang.Strings;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor(access = PROTECTED)
public class User extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String email;

    @Column(length = 20)
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @Builder
    public User(Long id, String email, String password, UserRole userRole) {
        this.id = id;
        this.userRole = userRole;
        setEmail(email);
        setPassword(password);
    }

    public static User of(Long id) {
        return User.builder().id(id).build();
    }

    private void setEmail(String email) {
        this.email = email;
    }

    private void setPassword(String password) {
        checkArgument(Strings.hasText(password) && password.length() <= 20, INVALID_INPUT);
        this.password = password;
    }

    public void validatePassword(PasswordEncoder encoder, String password) {
        if (!encoder.matches(password, this.password)) {
            throw new UserNoPermissionException();
        }
    }

    public void validateUserPermission(Long loginId) {
        if (!loginId.equals(this.id)) {
            throw new UserNoPermissionException();
        }
    }
}
