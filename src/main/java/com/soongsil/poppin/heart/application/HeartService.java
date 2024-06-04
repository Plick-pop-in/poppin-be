package com.soongsil.poppin.heart.application;

import com.soongsil.poppin.heart.application.response.PostHeart;
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
        Boolean isLiked = HeartRepository.existsByPopupPopupIdAndMemberUserId(popupId, userId);

        return isLiked;
    }

    public PostHeart addHeart( long popupId, long userId){
        Boolean state= null;
        Long likeCount = null;
        Long heartId = HeartRepository.findHeartById(popupId, userId);

        if(heartId==null){
            HeartRepository.addHeart(popupId, userId);
            likeCount = HeartRepository.countHeartByPopup(popupId);
            state = true;
        }else{
            state=false;
        }

        PostHeart postHeart = new PostHeart(state, likeCount);

        return postHeart;
    }

    public PostHeart deleteHeart(Long popupId, Long userId){
        Boolean state= null;
        Long likeCount = null;
        Long heartId = HeartRepository.findHeartById(popupId, userId);

        if(heartId!=null){
            HeartRepository.deleteHeart(heartId);
            likeCount = HeartRepository.countHeartByPopup(popupId);
            state = true;
        }else{
            state=false;
        }

        PostHeart postHeart = new PostHeart(state, likeCount);
        return postHeart;
    }
}
