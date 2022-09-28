package com.payhere.gagebu.domain.record.model;

import static com.google.common.base.Preconditions.*;
import static com.payhere.gagebu.common.exception.error.ErrorMessage.*;
import static lombok.AccessLevel.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.payhere.gagebu.common.model.BaseEntity;
import com.payhere.gagebu.domain.record.exception.RecordNoPermissionException;
import com.payhere.gagebu.domain.user.model.User;

import io.jsonwebtoken.lang.Strings;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Record extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, length = 20)
    private String name;

    @Column(nullable = false)
    private Integer money;

    @Column(nullable = false, length = 60)
    private String memo;

    @ManyToOne(optional = false)
    private User user;

    @Column(nullable = false)
    private boolean deleted;

    @Builder
    public Record(Long id, String name, Integer money, String memo, User user, boolean deleted) {
        this.id = id;
        this.user = user;
        this.deleted = deleted;
        setName(name);
        setMoney(money);
        setMemo(memo);
    }

    private void setName(String name) {
        checkArgument(Strings.hasText(name) && name.length() <= 20, INVALID_INPUT);
        this.name = name;
    }

    private void setMemo(String memo) {
        checkArgument(Strings.hasText(memo) && memo.length() <= 60, INVALID_INPUT);
        this.memo = memo;
    }

    private void setMoney(Integer money) {
        checkArgument(0 <= money && money <= 100_000_000, INVALID_INPUT);
        this.money = money;
    }

    public void editMoney(Integer money) {
        setMoney(money);
    }

    public void editMemo(String memo) {
        setMemo(memo);
    }

    public void validateUserPermission(Long userId) {
        if (!userId.equals(user.getId())) {
            throw new RecordNoPermissionException();
        }
    }

}
