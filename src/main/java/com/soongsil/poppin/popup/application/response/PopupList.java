package com.soongsil.poppin.popup.application.response;

import lombok.Getter;

@Getter
public class PopupList {
    private final Long popupId;
    private final String popupImage;
    private final String popupName;
    private final String popupPeriod;
    private final Long likeCount;

    public PopupList(Long popupId, String popupImage, String popupName, String popupPeriod, Long likeCount) {
        this.popupId = popupId;
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
