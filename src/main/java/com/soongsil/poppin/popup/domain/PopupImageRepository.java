package com.soongsil.poppin.popup.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PopupImageRepository extends JpaRepository<PopupImage, Long> {

    // 지정된 개수(count)만큼 랜덤하게 이미지 목록을 조회하는 쿼리
    @Query(value = "SELECT * FROM img WHERE (popup_id, popup_image_id) IN (SELECT popup_id, MIN(popup_image_id) FROM img GROUP BY popup_id) LIMIT :count", nativeQuery = true)
    List<PopupImage> findRandomImages(int count);


    //popupId에 해당하는 모든 이미지 목록 조회하는 쿼리
    @Query(value = "SELECT * FROM img WHERE popup_id = :popupId", nativeQuery = true)
    List<PopupImage> findPopupImagesById(Long popupId);
}
