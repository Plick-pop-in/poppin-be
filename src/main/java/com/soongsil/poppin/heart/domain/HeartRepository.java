package com.soongsil.poppin.heart.domain;


import com.soongsil.poppin.popup.domain.Popup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HeartRepository extends JpaRepository<Heart, Long> {

}

