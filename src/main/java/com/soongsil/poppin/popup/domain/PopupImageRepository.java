package com.soongsil.poppin.popup.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PopupImageRepository extends JpaRepository<PopupImage, Long> {

    // 지정된 개수(count)만큼 랜덤하게 이미지 목록을 조회하는 쿼리
    @Query(value = "SELECT * FROM IMG ORDER BY RAND() LIMIT :count", nativeQuery = true)
    List<PopupImage> findRandomImages(int count);
}
