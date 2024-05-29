package com.soongsil.poppin.popup.application.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class InProgressPopup {
    private String popupImageUrl;
    private String popupName;
    private String popupPeriod;
}
