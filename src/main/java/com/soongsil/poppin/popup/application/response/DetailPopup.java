package com.soongsil.poppin.popup.application.response;

import lombok.Getter;

import java.util.List;

@Getter
public class DetailPopup {

    private final String popupName; //팝업이름
    private final String popupTime;
    private final String popupIntro;
    private final String popupPageLink;
    private final String popupLocation;
    private final String popupCity;
    private final String popupLocal;
    private final String popupPeriod;
    private final Long likeCount;
    private final List<String> popupImgUrls;

    public DetailPopup(String popupName, String popupTime, String popupIntro, String popupPageLink, String popupLocation, String popupCity, String popupLocal, String popupPeriod, Long likeCount, List<String> popupImgUrls) {
        this.popupName = popupName;
        this.popupTime = popupTime;
        this.popupIntro = popupIntro;
        this.popupPageLink = popupPageLink;
        this.popupLocation = popupLocation;
        this.popupCity = popupCity;
        this.popupLocal = popupLocal;
        this.popupPeriod = popupPeriod;
        this.likeCount = likeCount;
        this.popupImgUrls = popupImgUrls;
    }
}

