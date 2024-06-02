package com.soongsil.poppin.popup.presentation;


import com.soongsil.poppin.category.domain.Category;
import com.soongsil.poppin.global.response.ResponseDto;
import com.soongsil.poppin.popup.application.PopupSearchService;
import com.soongsil.poppin.popup.application.response.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/v1/popup")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
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

    // 메인페이지 진행중 팝업 불러오기
    @GetMapping("/in-progress")
    public ResponseDto<List<InProgressPopup>> getInProgressPopups(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "5") int size) {
        List<InProgressPopup> inProgressPopups = popupSearchService.getInProgressPopups(page, size);
        return ResponseDto.map(HttpStatus.OK.value(), "진행 중인 팝업 정보 불러오기 성공", inProgressPopups);
    }

    //팝업 디테일
    @GetMapping("/popupdetail/{popupId}")
    public ResponseDto<DetailPopup> getDetailPopupById(@PathVariable(name = "popupId") Long popupId) {
        try {
            DetailPopup detailPopup = popupSearchService.getDetailPopupById(popupId);
            if (detailPopup != null) {
                return ResponseDto.map(HttpStatus.OK.value(), "팝업 디테일 정보 불러오기 성공", detailPopup);
            } else {
                return ResponseDto.map(HttpStatus.NOT_FOUND.value(), "해당 ID에 대한 팝업 디테일을 찾을 수 없습니다.", null);
            }
        } catch (Exception ex) {
            Throwable targetException = ex.getCause();
            if (targetException != null) {
                System.out.println("원인 예외: " + targetException.getMessage());
            } else {
                System.out.println("예외 발생: " + ex.getMessage());
            }
            return ResponseDto.map(HttpStatus.NOT_FOUND.value(), "getDetailPopupById 에러 발생.", null);
        }
    }

    // 라이브 리스트 불러오기
    @GetMapping("/live")
    public ResponseDto<List<Live>> getLiveLists(
            @RequestParam(value = "keyword", required = false) String keyword) {
        // 키워드 출력
        System.out.println("Keyword: " + keyword);

        List<Live> liveList = popupSearchService.getLiveLists(keyword);
        return ResponseDto.map(HttpStatus.OK.value(), "라이브 리스트 불러오기", liveList);
    }

    @GetMapping("/popuplist")
    public ResponseDto<List<PopupList>> getPopupListWithFilter(
            @RequestParam(value = "fashion") Boolean fashion,
            @RequestParam(value = "beauty") Boolean beauty,
            @RequestParam(value = "food") Boolean food,
            @RequestParam(value = "celeb") Boolean celeb,
            @RequestParam(value = "digital") Boolean digital,
            @RequestParam(value = "character") Boolean character,
            @RequestParam(value = "living") Boolean living,
            @RequestParam(value = "game") Boolean game,
            @RequestParam(value = "period") String period,
            @RequestParam(value = "search") String search
    ){
        // 전달된 카테고리 정보를 CategoryFilter 객체로 변환
        Category category = new Category(fashion,beauty,food,celeb,character,living,digital,game);

        List<PopupList> popupList = null;
        if (search.isEmpty()){
            search=null;
        }

        popupList= popupSearchService.getPopupListWithSearchAndFilter(category, period, search);

        return ResponseDto.map(HttpStatus.OK.value(), "필터링한 팝업 리스트 불러오기 성공!", popupList);
    }
}