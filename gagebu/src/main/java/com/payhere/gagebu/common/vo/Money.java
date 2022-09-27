package com.payhere.gagebu.common.vo;

import static com.google.common.base.Preconditions.*;
import static com.payhere.gagebu.common.exception.error.ErrorMessage.*;
import static lombok.AccessLevel.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = PROTECTED)
public class Money {

    private static final int MIN_VALUE = 0;

    private static final int MAX_VALUE = 100_000_000;

    @Column(name = "money")
    private int value;

    public Money(int value) {
        checkArgument(MIN_VALUE <= value && value <= MAX_VALUE, INVALID_INPUT);
        this.value = value;
    }

}
