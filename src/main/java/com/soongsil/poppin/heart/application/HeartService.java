package com.soongsil.poppin.heart.application;

import com.soongsil.poppin.heart.domain.Heart;
import com.soongsil.poppin.heart.domain.HeartRepository;
import com.soongsil.poppin.popup.domain.PopupRepository;
import jakarta.persistence.EntityNotFoundException;
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

    public void addHeart( long popupId, long userId){

    }

    public void deleteHeart( long popupId, long userId){

    }
}
