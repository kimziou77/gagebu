package com.payhere.gagebu.domain.record.model;

import static lombok.AccessLevel.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.payhere.gagebu.common.model.BaseEntity;

import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = PROTECTED)
public class RecordHistory extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Record record;

}
