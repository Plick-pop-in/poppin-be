package com.soongsil.poppin.popup.application.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WishPopup {
    private final Long popupId;
    private final String popupImage;
    private final String popupName;
    private final Long heart;
}
