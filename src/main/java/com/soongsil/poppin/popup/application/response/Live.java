package com.soongsil.poppin.popup.application.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Live {
    private final String popupImage;
    private final String popupName;
    private final String popupLocation;
    private final String popupCity;
    private final String popupLocal;
    private final String popupPeriod;
    private final long joinedPeopleCnt;
}
