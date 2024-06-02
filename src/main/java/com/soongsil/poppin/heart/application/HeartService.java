package com.soongsil.poppin.heart.application;

import com.soongsil.poppin.heart.domain.Heart;
import com.soongsil.poppin.heart.domain.HeartRepository;
import com.soongsil.poppin.popup.domain.PopupRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HeartService {
    private final HeartRepository HeartRepository;

    public Boolean getIsLikedById( long popupId, long userId){
        Boolean isLiked = HeartRepository.getIsLikedById(popupId, userId);

        return isLiked;
    }

    public void addHeart( long popupId, long userId){

    }

    public void deleteHeart( Long popupId, Long userId){
        // userId와 popupId로 해당 하트를 찾습니다.
        Optional<Heart> heartOptional = HeartRepository.findHeartById(popupId,userId);

        // 하트를 찾았는지 확인하고 삭제합니다.
        heartOptional.ifPresent(HeartRepository::delete);
    }
}
