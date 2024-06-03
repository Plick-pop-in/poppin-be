package com.soongsil.poppin.heart.presentation;


import com.soongsil.poppin.global.response.ResponseDto;
import com.soongsil.poppin.heart.application.HeartService;
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
    public ResponseDto<String> addHeart(@RequestParam(value = "userId") Long userId, @RequestParam(value = "popupId") Long popupId) {
        Boolean state = HeartService.addHeart(popupId, userId);
        if (state){
            return ResponseDto.map(HttpStatus.OK.value(), "하트 추가 성공", "찜목록에 추가되었습니다.");
        }else {
            return ResponseDto.map(HttpStatus.OK.value(), "하트 추가 실패", "이미 찜 처리된 요청입니다.");
        }
    }

    @DeleteMapping("/deleteHeart")
    public ResponseDto<String> deleteHeart(@RequestParam(value = "userId") Long userId, @RequestParam(value = "popupId") Long popupId) {
        Boolean state = HeartService.deleteHeart(popupId, userId);
        if(state) {
            return ResponseDto.map(HttpStatus.OK.value(), "하트 삭제 성공", "찜목록에서 삭제되었습니다.");
        }else {
            return ResponseDto.map(HttpStatus.OK.value(), "하트 삭제 실패", "찜 목록에 존재하지 않는 요청입니다.");
        }
    }
}
