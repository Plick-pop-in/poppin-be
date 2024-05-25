package com.soongsil.poppin.popup.application;

import com.soongsil.poppin.popup.application.response.DetailPopup;
import com.soongsil.poppin.popup.application.response.TopPopup;
import com.soongsil.poppin.popup.domain.Popup;
import com.soongsil.poppin.popup.domain.PopupImage;
import com.soongsil.poppin.popup.domain.PopupImageRepository;
import com.soongsil.poppin.popup.application.response.ImgUrlList;
import com.soongsil.poppin.popup.application.exception.PopupException;
import com.soongsil.poppin.global.response.ErrorCode;
import com.soongsil.poppin.popup.domain.PopupRepository;
import com.soongsil.poppin.heart.domain.HeartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;

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
        Pageable pageable = PageRequest.of(0, 3); // 첫 번째 페이지에서 최대 3개 결과 요청
        Page<Object[]> top3PopupsWithMostLikes = popupRepository.findTop3ByOrderByLikeCountDesc(pageable);
        return top3PopupsWithMostLikes.getContent().stream()
                .map(this::mapToPopup)
                .collect(Collectors.toList());
    }

    private TopPopup mapToPopup(Object[] row) {
        String popupImage = (String) row[0];
        String popupName = (String) row[1];
        String popupPeriod = (String) row[2];
        Long likeCount = (Long) row[3];

        return new TopPopup(popupImage, popupName, popupPeriod, likeCount);
    }

    //팝업 상세페이지
    public DetailPopup getDetailPopupById(Long popupId){
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
        String popupLocal= popup.getPopupLocal();

        String startDate = popup.getPopupStartDate().format(DateTimeFormatter.ofPattern("yy.MM.dd"));
        String endDate = popup.getPopupEndDate().format(DateTimeFormatter.ofPattern("yy.MM.dd"));
        String popupPeriod = startDate +" - " + endDate;

        // 하트개수 가져오기
        Long likeCount = heartRepository.countHeartById(popupId);

        return new DetailPopup(popupName, popupTime, popupIntro, popupPageLink, popupLocation, popupCity, popupLocal, popupPeriod, likeCount, imageUrlList);
    }

}
