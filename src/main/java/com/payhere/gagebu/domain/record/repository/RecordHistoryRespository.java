package com.payhere.gagebu.domain.record.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.payhere.gagebu.domain.record.model.RecordHistory;

public interface RecordHistoryRespository extends JpaRepository<RecordHistory, Long> {

}
