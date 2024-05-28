package com.soongsil.poppin.popup.domain;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface LiveRepository extends JpaRepository<Popup,Long> {

    // 종료되지 않은 팝업 조회 (라이브 리스트 불러오기)
    @Query("SELECT p" +
            " FROM Popup p " +
            "WHERE :currentDate < p.popupEndDate")
    List<Popup> findDateBeforeEndDate(LocalDateTime currentDate, Pageable pageable);
}
