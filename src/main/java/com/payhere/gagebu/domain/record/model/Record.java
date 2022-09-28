package com.payhere.gagebu.domain.record.model;

import static lombok.AccessLevel.*;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.payhere.gagebu.common.model.BaseEntity;
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

    @Column(nullable = false, length = 20)
    private String name;

    @Column(nullable = false)
    private Integer money;

    @Column(nullable = false, length = 60)
    private String memo;

    @ManyToOne(optional = false)
    private User user;

    @Column(nullable = false)
    private boolean deleted = false;

    private LocalDateTime deletedAt;

    @Builder
    public Record(Long id, String name, Integer money, String memo, User user, boolean deleted) {
        this.id = id;
        this.name = name;
        this.memo = memo;
        this.money = money;
        this.user = user;
        this.deleted = deleted;
    }

    public void editMoney(Integer money) {
        this.money = money;
    }

    public void editMemo(String memo) {
        this.memo = memo;
    }

    public void delete() {
        this.deleted = true;
        this.deletedAt = LocalDateTime.now();
    }

    public void restore() {
        this.deleted = false;
        this.deletedAt = null;
    }

    public void validateUserPermission(Long userId) {
        if (!userId.equals(user.getId())) {
            throw new RecordNoPermissionException();
        }
    }

}
