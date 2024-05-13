package com.soongsil.poppin.popup.presentation;


import com.soongsil.poppin.global.response.ResponseDto;
import com.soongsil.poppin.popup.application.PopupSearchService;
import com.soongsil.poppin.popup.application.response.ImgUrlList;
import com.soongsil.poppin.popup.application.response.TopPopup;
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

    //메인페이지 랜덤이미지(5개) 불러오기
    @GetMapping("/random-images")
    public ResponseDto<ImgUrlList> getRandomPopupImageUrls() {
            ImgUrlList imgUrlList = popupSearchService.getRandomPopupImageUrls(5); // 5개의 랜덤 이미지 URL 가져오기
            return ResponseDto.map(HttpStatus.OK.value(),"랜덤 팝업 이미지 5개 불러오기 성공", imgUrlList);
    }

    //메인페이지 좋아요 top3 불러오기
    @GetMapping("/top3")
    public ResponseDto<List<TopPopup>> getTop3PopupsWithMostLikes() {
        List<TopPopup> top3Popups = popupSearchService.getTop3PopupsWithMostLikes();
        return ResponseDto.map(HttpStatus.OK.value(), "좋아요 TOP3 팝업 정보 불러오기 성공", top3Popups);
    }


}