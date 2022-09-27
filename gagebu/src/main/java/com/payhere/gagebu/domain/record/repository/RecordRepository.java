package com.payhere.gagebu.domain.record.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.payhere.gagebu.domain.record.model.Record;

public interface RecordRepository extends JpaRepository<Record, Long> {

    List<Record> findByUserId(Long userId);
}
