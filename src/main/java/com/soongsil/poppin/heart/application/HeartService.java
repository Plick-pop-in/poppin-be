package com.soongsil.poppin.heart.application;

import com.soongsil.poppin.heart.domain.HeartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HeartService {
    private final HeartRepository HeartRepository;

    public Boolean getIsLikedById( long popupId, long userId){
        Boolean isLiked = HeartRepository.getIsLikedById(popupId, userId);

        return isLiked;
    }
}
