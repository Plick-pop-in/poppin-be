package com.soongsil.poppin.popup.application.response;

import lombok.Getter;

@Getter
public class TopPopup {
    private final String popupImage;
    private final String popupName;
    private final String popupPeriod;
    private final Long likeCount;

    public TopPopup(String popupImage, String popupName, String popupPeriod, Long likeCount) {
        this.popupImage = popupImage;
        this.popupName = popupName;
        this.popupPeriod = popupPeriod;
        this.likeCount = likeCount;
    }

    public String getPopupImage() {
        return popupImage;
    }

    public String getPopupName() {
        return popupName;
    }

    public String getPopupPeriod() {
        return popupPeriod;
    }

    public Long getLikeCount() {
        return likeCount;
    }
}
