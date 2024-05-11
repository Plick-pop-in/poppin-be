package com.soongsil.poppin.popup.application.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ImgUrlList {
    private final List<String> popupImgUrls;
}
