package com.payhere.gagebu.domain.record.model;

import static lombok.AccessLevel.*;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.payhere.gagebu.common.model.BaseEntity;
import com.payhere.gagebu.common.vo.Money;
import com.payhere.gagebu.domain.record.exception.RecordNoPermissionException;
import com.payhere.gagebu.domain.user.model.User;

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

    private String name;

    @Embedded
    private Money money;

    private String memo;

    @Enumerated(EnumType.STRING)
    private Category category;

    @ManyToOne
    private User user;

    private boolean deleted;

    @Builder
    public Record(Long id, String name, Money money, String memo, Category category, User user, boolean deleted) {
        this.id = id;
        this.name = name;
        this.money = money;
        this.memo = memo;
        this.category = category;
        this.user = user;
        this.deleted = deleted;
    }

    public void validateUserPermission(Long userId) {
        if (!userId.equals(user.getId())) {
            throw new RecordNoPermissionException();
        }
    }

    public void editMoney(Integer money) {
        this.money = new Money(money);
    }

    public void editMemo(String memo) {
        this.memo = memo;
    }

}
