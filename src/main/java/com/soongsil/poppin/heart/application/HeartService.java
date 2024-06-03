package com.soongsil.poppin.heart.application;

import com.soongsil.poppin.heart.domain.Heart;
import com.soongsil.poppin.heart.domain.HeartRepository;
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
        Long heartId = HeartRepository.findHeartById(popupId, userId);
        HeartRepository.addHeart(popupId, userId);
    }

    public void deleteHeart( Long popupId, Long userId){
        Long heartId = HeartRepository.findHeartById(popupId, userId);
        HeartRepository.deleteHeart(heartId);
    }
}
