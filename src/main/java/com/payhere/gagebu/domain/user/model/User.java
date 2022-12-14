package com.payhere.gagebu.domain.user.model;

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

    @Column(length = 200)
    private String password; /* encoded password */

    private boolean verified = false;

    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @Builder
    public User(Long id, String email, String password, boolean verified, UserRole userRole) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.verified = verified;
        this.userRole = userRole;
    }

    public static User of(Long id) {
        return User.builder().id(id).build();
    }

    public void verify() {
        this.verified = true;
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
