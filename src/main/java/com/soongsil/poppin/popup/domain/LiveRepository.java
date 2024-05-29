package com.soongsil.poppin.popup.domain;

import com.soongsil.poppin.popup.application.response.Live;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface LiveRepository extends JpaRepository<Popup,Long> {

    // 팝업 종료 이전 + 키워드 검색
    @Query("SELECT p " +
            "FROM Popup p " +
            "WHERE (:keyword IS NULL OR LOWER(p.popupName) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
            "AND p.popupEndDate > :currentDate")
    List<Popup> findPopupByKeywordAndEndDateBefore(@Param("keyword") String keyword, @Param("currentDate") LocalDateTime currentDate);

    // 팝업 별 참여한 인원 수 가져오기
    @Query("SELECT COUNT(uc) " +
            "FROM UserChat uc " +
            "WHERE uc.popup.popupId = :popupId")
    long getJoinedPeopleCnt(@Param("popupId") Long popupId);
}
