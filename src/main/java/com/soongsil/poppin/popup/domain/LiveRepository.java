package com.soongsil.poppin.popup.domain;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface LiveRepository extends JpaRepository<Popup,Long> {

    // 종료되지 않은 팝업 조회 (라이브 리스트 불러오기)
    @Query("SELECT p" +
            " FROM Popup p " +
            "WHERE :currentDate < p.popupEndDate")
    List<Popup> findPopupBeforeEndDate(LocalDateTime currentDate, Pageable pageable);

    // 팝업 별 참여한 인원 수 가져오기
    @Query("SELECT COUNT(uc.userId) " +
            "FROM UserChat uc " +
            "GROUP BY uc.popupId")
    long getJoinedPeopleCnt(@Param("popuupId") Long popupId);
}
