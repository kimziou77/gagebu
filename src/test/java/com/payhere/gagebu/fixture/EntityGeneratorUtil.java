package com.payhere.gagebu.fixture;

import java.util.UUID;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.payhere.gagebu.common.vo.Money;
import com.payhere.gagebu.domain.record.model.Category;
import com.payhere.gagebu.domain.record.model.Record;
import com.payhere.gagebu.domain.record.model.Record.RecordBuilder;
import com.payhere.gagebu.domain.user.model.User;
import com.payhere.gagebu.domain.user.model.User.UserBuilder;
import com.payhere.gagebu.domain.user.model.UserRole;

public abstract class EntityGeneratorUtil {

    private static PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // Basic Entity
    public static User makeUser(String password) {
        return basicUserBuilder(password).build();
    }

    public static Record makeRecord(User user) {
        return basicRecordBuilder(user).build();
    }

    // Basic Entity with Id
    public static User makeUserWithId(Long id, String password) {
        return basicUserBuilder(password).id(id).build();
    }

    public static Record makeRecordWithId(Long id, User user) {
        return basicRecordBuilder(user).id(id).build();
    }

    // private Builder
    private static UserBuilder basicUserBuilder(String password) {
        String email = UUID.randomUUID().toString().substring(26) + "@test.com";
        return User.builder()
            .email(email)
            .password(passwordEncoder.encode(password))
            .userRole(UserRole.LOGIN);
    }

    private static RecordBuilder basicRecordBuilder(User user) {
        String name = "테스트 이름 - " + UUID.randomUUID().toString().substring(26);
        String memo = "테스트 메모 -" + UUID.randomUUID().toString().substring(26);

        return Record.builder()
            .name(name)
            .money(new Money(1000))
            .memo(memo)
            .category(Category.FOOD)
            .user(user)
            .deleted(false);
    }
}
