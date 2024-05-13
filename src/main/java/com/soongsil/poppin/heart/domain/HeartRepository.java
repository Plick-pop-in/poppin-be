package com.soongsil.poppin.heart.domain;


import com.soongsil.poppin.popup.domain.Popup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface HeartRepository extends JpaRepository<Heart, Long> {
    //heart 개수 Count
    @Query(value = "SELECT count(*) FROM Heart WHERE popup_id = :popupId", nativeQuery = true)
    Long countHeartById(Long popupId);

}

