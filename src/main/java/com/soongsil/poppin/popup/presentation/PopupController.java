package com.soongsil.poppin.popup.presentation;


import com.soongsil.poppin.global.response.ResponseDto;
import com.soongsil.poppin.popup.application.PopupSearchService;
import com.soongsil.poppin.popup.application.response.ImgUrlList;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/v1/popup")
@RequiredArgsConstructor
public class PopupController {
    private final PopupSearchService popupSearchService;

    @GetMapping("/random-images")
    public ResponseDto<ImgUrlList> getRandomPopupImageUrls() {
            ImgUrlList imgUrlList = popupSearchService.getRandomPopupImageUrls(5); // 5개의 랜덤 이미지 URL 가져오기
            return ResponseDto.map(HttpStatus.OK.value(),"랜덤 팝업 이미지 5개 불러오기 성공", imgUrlList);
    }
}