package com.soongsil.poppin.popup.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PopupRepository extends JpaRepository<Popup, Long> {

    @Query("SELECT i.popupImageUrl, p.popupName, " +
            "CONCAT(FORMAT(p.popupStartDate, 'yyyy-MM-dd'), ' - ', FORMAT(p.popupEndDate, 'yyyy-MM-dd')) AS popupPeriod, " +
            "(SELECT COUNT(h) FROM Heart h WHERE h.popup = p) AS likeCount " +
            "FROM Popup p " +
            "JOIN p.popupImages i " +
            "GROUP BY p.popupId, i.popupImageUrl, p.popupName, p.popupStartDate, p.popupEndDate " +
            "ORDER BY likeCount DESC")
    Page<Object[]> findTop3ByOrderByLikeCountDesc(Pageable pageable);
}


