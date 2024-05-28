package com.soongsil.poppin.popup.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PopupRepository extends JpaRepository<Popup, Long> {

    // 좋아요가 가장 많은 상위 3개 팝업 ID 가져오기
    @Query("SELECT p.popupId " +
            "FROM Popup p " +
            "JOIN Heart h ON p.popupId = h.popup.popupId " +
            "GROUP BY p.popupId " +
            "ORDER BY COUNT(h) DESC " +
            "LIMIT 3")
    List<Long> findTop3PopupIdsWithMostLikes();

    // 메인페이지 좋아요 top3 가져오는 쿼리
    @Query("SELECT p.popupId, i.popupImageUrl, p.popupName, " +
            "CONCAT(FUNCTION('DATE_FORMAT', p.popupStartDate, '%Y-%m-%d'), ' - ', FUNCTION('DATE_FORMAT', p.popupEndDate, '%Y-%m-%d')) AS popupPeriod, " +
            "(SELECT COUNT(*) FROM Heart h WHERE h.popup = p) AS likeCount " +
            "FROM Popup p " +
            "LEFT JOIN p.popupImages i " +
            "GROUP BY p.popupId, i.popupImageUrl, p.popupName, p.popupStartDate, p.popupEndDate ")
    Page<Object[]> findTop3ByOrderByLikeCountDesc(Pageable pageable);

    //id에 해당하는 값 가져오는 쿼리
    @Query(value = "SELECT * FROM popup WHERE popup_id = :popupId", nativeQuery = true)
    Popup findPopupById(Long popupId);
}