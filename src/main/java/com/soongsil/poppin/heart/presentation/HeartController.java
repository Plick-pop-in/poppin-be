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
}
