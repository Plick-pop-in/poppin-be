package com.soongsil.poppin.popup.application.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MapPopup {
    private final long popupId;
    private final String popupName;
    private final String popupLocation;
    private final String popupCity;
    private final String popupLocal;
}
