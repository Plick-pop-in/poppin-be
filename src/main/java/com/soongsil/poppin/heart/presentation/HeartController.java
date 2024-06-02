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
        HeartService.addHeart(popupId, userId);
        return ResponseDto.map(HttpStatus.OK.value(), "하트 추가 성공", "하트가 성공적으로 추가되었습니다.");
    }

    @DeleteMapping("/deleteHeart")
    public ResponseDto<String> deleteHeart(@RequestParam(value = "userId") Long userId, @RequestParam(value = "popupId") Long popupId) {
        HeartService.deleteHeart(popupId, userId);
        return ResponseDto.map(HttpStatus.OK.value(), "하트 삭제 성공", "하트가 성공적으로 삭제되었습니다.");
    }
}
