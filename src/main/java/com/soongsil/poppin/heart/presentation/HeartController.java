package com.soongsil.poppin.heart.presentation;


import com.soongsil.poppin.global.response.ResponseDto;
import com.soongsil.poppin.heart.application.HeartService;
import com.soongsil.poppin.heart.application.response.PostHeart;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "v1/heart")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class HeartController {
    private final HeartService HeartService;

    @GetMapping("/isLike")
    public ResponseDto<Boolean> getIsLikedById(@RequestParam(value = "userId") Long userId, @RequestParam(value = "popupId") Long popupId) {
        Boolean isLiked = HeartService.getIsLikedById(popupId, userId);
        return ResponseDto.map(HttpStatus.OK.value(),"좋아요 여부", isLiked);
    }

    @PostMapping("/addHeart")
    public ResponseDto<PostHeart> addHeart(@RequestParam(value = "userId") Long userId, @RequestParam(value = "popupId") Long popupId) {
        PostHeart postHeart = HeartService.addHeart(popupId, userId);
        Boolean state = postHeart.getState();
        if (state){
            return ResponseDto.map(HttpStatus.OK.value(), "하트 추가 성공", postHeart);
        }else {
            return ResponseDto.map(HttpStatus.OK.value(), "하트 추가 실패", postHeart);
        }
    }

    @DeleteMapping("/deleteHeart")
    public ResponseDto<PostHeart> deleteHeart(@RequestParam(value = "userId") Long userId, @RequestParam(value = "popupId") Long popupId) {
        PostHeart postHeart = HeartService.deleteHeart(popupId, userId);
        Boolean state = postHeart.getState();
        if(state) {
            return ResponseDto.map(HttpStatus.OK.value(), "하트 삭제 성공", postHeart);
        }else {
            return ResponseDto.map(HttpStatus.OK.value(), "찜 목록에 존재하지 않는 요청입니다.", postHeart);
        }
    }
}
