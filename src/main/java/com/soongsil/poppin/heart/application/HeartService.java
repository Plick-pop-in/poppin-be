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

    public Boolean addHeart( long popupId, long userId){
        Long heartId = HeartRepository.findHeartById(popupId, userId);
        Boolean state= null;
        if(heartId==null){
            HeartRepository.addHeart(popupId, userId);
            state = true;
        }else{
            state=false;
        }
        return state;
    }

    public Boolean deleteHeart( Long popupId, Long userId){
        Long heartId = HeartRepository.findHeartById(popupId, userId);
        Boolean state= null;
        if(heartId!=null){
            HeartRepository.deleteHeart(heartId);
            state = true;
        }else{
            state=false;
        }
        return state;
    }
}
