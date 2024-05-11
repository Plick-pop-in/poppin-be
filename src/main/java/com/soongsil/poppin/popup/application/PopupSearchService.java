package com.soongsil.poppin.popup.application;

import com.soongsil.poppin.popup.domain.PopupImage;
import com.soongsil.poppin.popup.domain.PopupImageRepository;
import com.soongsil.poppin.popup.application.response.ImgUrlList;
import com.soongsil.poppin.popup.application.exception.PopupException;
import com.soongsil.poppin.global.response.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PopupSearchService {
    private final PopupImageRepository popupImageRepository;

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
}
