package com.soongsil.poppin.heart.domain;


import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HeartRepository extends JpaRepository<Heart, Long> {
    // 메인페이지 사용 팝업별 heart 개수 count
    @Query("SELECT COUNT(h) FROM Heart h WHERE h.popup.id = ?1")
    Long countHeartByPopup(Long popupId);

    @Query("SELECT COUNT(h) > 0 FROM Heart h WHERE h.popup.id = :popupId AND h.member.id = :userId")
    Boolean getIsLikedById(Long popupId, Long userId);

    @Query("SELECT h.heartId FROM Heart h WHERE h.popup.id = :popupId AND h.member.id = :userId")
    Long findHeartById(Long popupId, Long userId);

    @Modifying
    @Transactional
    @Query("INSERT INTO Heart (member, popup, createdDate) SELECT m, p, CURRENT_TIMESTAMP FROM Member m, Popup p WHERE m.id = :userId AND p.id = :popupId")
    void addHeart(Long popupId, Long userId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Heart WHERE heartId = :heartId")
    void deleteHeart(Long heartId);

}

