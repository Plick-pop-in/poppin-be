package com.soongsil.poppin.heart.domain;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface HeartRepository extends JpaRepository<Heart, Long> {
    // 메인페이지 사용 팝업별 heart 개수 count
    @Query("SELECT COUNT(h) FROM Heart h WHERE h.popup.id = ?1")
    Long countHeartByPopup(Long popupId);

    //heart 개수 Count
    @Query(value = "SELECT count(*) FROM Heart WHERE popup_id = :popupId", nativeQuery = true)
    Long countHeartById(Long popupId);

}

