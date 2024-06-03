package com.soongsil.poppin.popup.application;

import com.soongsil.poppin.category.domain.Category;
import com.soongsil.poppin.popup.application.response.*;
import com.soongsil.poppin.popup.domain.*;
import com.soongsil.poppin.popup.application.exception.PopupException;
import com.soongsil.poppin.global.response.ErrorCode;
import com.soongsil.poppin.heart.domain.HeartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PopupSearchService {
    private final PopupImageRepository popupImageRepository;
    private final PopupRepository popupRepository;
    private final HeartRepository heartRepository;
    private final LiveRepository liveRepository;

    // 메인페이지 랜덤이미지(5개) 불러오기
    public ImgUrlList getRandomPopupImageUrls(int count) {
        List<String> imageUrlList = new ArrayList<>();

        List<PopupImage> randomPopupImages = popupImageRepository.findRandomImages(count);
        if (randomPopupImages.isEmpty()) {
            throw new PopupException(ErrorCode.POPUP_IMG_NOT_FOUND);
        }

        for (PopupImage popupImage : randomPopupImages) {
            imageUrlList.add(popupImage.getPopupImageUrl());
        }

        return new ImgUrlList(imageUrlList);
    }

    // 메인페이지 좋아요 top3 불러오기
    public List<TopPopup> getTop3PopupsWithMostLikes() {
        // 좋아요가 가장 많은 상위 3개 팝업 ID 가져오기
        List<Long> top3PopupIds = popupRepository.findTop3PopupIdsWithMostLikes();

        // 최상위 3개 팝업에 대한 상세 정보 조회
        List<TopPopup> top3Popups = new ArrayList<>();
        for (Long popupId : top3PopupIds) {
            Popup popup = popupRepository.findById(popupId)
                    .orElseThrow(() -> new PopupException(ErrorCode.POPUP_NOT_FOUND));

            String popupImage = popup.getPopupImages().get(0).getPopupImageUrl();
            String popupName = popup.getPopupName();
            String startDate = popup.getPopupStartDate().format(DateTimeFormatter.ofPattern("yy.MM.dd"));
            String endDate = popup.getPopupEndDate().format(DateTimeFormatter.ofPattern("yy.MM.dd"));
            String popupPeriod = startDate + " - " + endDate;
            Long likeCount = heartRepository.countHeartByPopup(popupId);

            top3Popups.add(new TopPopup(popupId,popupImage, popupName, popupPeriod, likeCount));
        }

        return top3Popups;
    }

    // 메인페이지 진행중 팝업 불러오기
    public List<InProgressPopup> getInProgressPopups(int page, int size) {
        List<InProgressPopup> inProgressPopups = new ArrayList<>();

        // 현재 날짜와 시간
        LocalDateTime currentDateTime = LocalDateTime.now();

        // 진행중인 팝업 조회
        List<Popup> inProgressPopupsList = popupRepository.findInProgressPopups(currentDateTime, PageRequest.of(page, size));

        for (Popup popup : inProgressPopupsList) {
            // 팝업 이미지 URL 가져오기
            String popupImageUrl = popup.getPopupImages().get(0).getPopupImageUrl();

            // 팝업 기간 구성 (yyyy.MM.dd - yyyy.MM.dd)
            String startDate = popup.getPopupStartDate().format(DateTimeFormatter.ofPattern("yy.MM.dd"));
            String endDate = popup.getPopupEndDate().format(DateTimeFormatter.ofPattern("yy.MM.dd"));
            String popupPeriod = startDate + " - " + endDate;

            // InProgressPopup 객체 생성 및 리스트에 추가
            inProgressPopups.add(new InProgressPopup(popupImageUrl, popup.getPopupName(), popupPeriod));
        }

        return inProgressPopups;
    }

    //팝업 상세페이지
    public DetailPopup getDetailPopupById(Long popupId) {
        //popupId로 해당 이미지 전부 가져오기
        List<PopupImage> popupImagesById = popupImageRepository.findPopupImagesById(popupId);
        if (popupImagesById.isEmpty()) {
            throw new PopupException(ErrorCode.POPUP_IMG_NOT_FOUND);
        }

        List<String> imageUrlList = popupImagesById.stream()
                .map(PopupImage::getPopupImageUrl)
                .collect(Collectors.toList());

        //나머지 팝업 정보 가져오기
        Popup popup = popupRepository.findPopupById(popupId);
        String popupName = popup.getPopupName(); //팝업이름
        String popupTime = popup.getPopupTime();
        String popupIntro = popup.getPopupIntro();
        String popupPageLink = popup.getPopupPageLink();
        String popupLocation = popup.getPopupLocation();
        String popupCity = popup.getPopupCity();
        String popupLocal = popup.getPopupLocal();

        String startDate = popup.getPopupStartDate().format(DateTimeFormatter.ofPattern("yy.MM.dd"));
        String endDate = popup.getPopupEndDate().format(DateTimeFormatter.ofPattern("yy.MM.dd"));
        String popupPeriod = startDate + " - " + endDate;

        // 하트개수 가져오기
        Long likeCount = heartRepository.countHeartByPopup(popupId);

        return new DetailPopup(popupName, popupTime, popupIntro, popupPageLink, popupLocation, popupCity, popupLocal, popupPeriod, likeCount, imageUrlList);
    }

    // 라이브 리스트 불러오기 (검색어 포함)
    public List<Live> getLiveLists(String keyword) {
        List<Live> liveList = new ArrayList<>();

        // 현재 날짜와 시간
        LocalDateTime currentDateTime = LocalDateTime.now();

        // 종료 이전 팝업 조회
        List<Popup> popupList = liveRepository.findPopupByKeywordAndEndDateBefore(keyword, currentDateTime);

        for (Popup popup : popupList) {
            String popupImageUrl = popup.getPopupImages().isEmpty() ? null : popup.getPopupImages().get(0).getPopupImageUrl();

            // 날짜 포맷을 변경하여 yy.MM.dd 형식으로 표시
            String popupPeriod = popup.getPopupStartDate().format(DateTimeFormatter.ofPattern("yy.MM.dd")) + " - " +
                    popup.getPopupEndDate().format(DateTimeFormatter.ofPattern("yy.MM.dd"));

            // 불필요한 변수를 줄이고, 바로 Live 객체를 생성하여 liveList에 추가
            liveList.add(new Live(
                    popupImageUrl,
                    popup.getPopupName(),
                    popup.getPopupLocation(),
                    popup.getPopupCity(),
                    popup.getPopupLocal(),
                    popupPeriod,
                    liveRepository.getJoinedPeopleCnt(popup.getPopupId())
            ));
        }

        return liveList;
    }

    //팝업 상세페이지 가져오기 search 값 + 필터링 값
    public List<PopupList> getPopupListWithSearchAndFilter(Category category, String period, String search) {
        List<PopupList> popupList = new ArrayList<>();
        List<Popup> popupsListWithPeriod = null;

        if (period.equals("all")) {     //기간 상관없이 모든 팝업
            popupsListWithPeriod = popupRepository.findAllPopupsWithFilters(category.isFashion(), category.isBeauty(), category.isFood(), category.isCeleb(), category.isCharactor(), category.isLiving(), category.isDigital(), category.isGame(), search);
        } else if (period.equals("open")) {     //현재 진행중인 팝업
            popupsListWithPeriod = popupRepository.findOpenPopupsWithFilters(category.isFashion(), category.isBeauty(), category.isFood(), category.isCeleb(), category.isCharactor(), category.isLiving(), category.isDigital(), category.isGame(),  search);
        } else if (period.equals("will")) {
            popupsListWithPeriod = popupRepository.findWillPopupsWithFilters(category.isFashion(), category.isBeauty(), category.isFood(), category.isCeleb(), category.isCharactor(), category.isLiving(), category.isDigital(), category.isGame(),  search);
        } else if (period.equals("close")) {   //끝난 팝업
            popupsListWithPeriod = popupRepository.findClosePopupsWithFilters(category.isFashion(), category.isBeauty(), category.isFood(), category.isCeleb(), category.isCharactor(), category.isLiving(), category.isDigital(), category.isGame(),  search);
        }

        if (popupsListWithPeriod != null) {
            for (Popup popup : popupsListWithPeriod) {

                Long popupId = popup.getPopupId();
                String popupImage = popup.getPopupImages().get(0).getPopupImageUrl();   // 팝업 이미지 URL 가져오기
                String popupName = popup.getPopupName();

                // 팝업 기간 구성 (yyyy.MM.dd - yyyy.MM.dd)
                String startDate = popup.getPopupStartDate().format(DateTimeFormatter.ofPattern("yy.MM.dd"));
                String endDate = popup.getPopupEndDate().format(DateTimeFormatter.ofPattern("yy.MM.dd"));
                String popupPeriod = startDate + " - " + endDate;
                Long likeCount = heartRepository.countHeartByPopup(popupId);

                // InProgressPopup 객체 생성 및 리스트에 추가
                popupList.add(new PopupList(popupId, popupImage, popupName, popupPeriod, likeCount));
            }
        }

        return popupList;
    }
}

