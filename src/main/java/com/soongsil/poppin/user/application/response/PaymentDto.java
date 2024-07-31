package com.soongsil.poppin.user.application.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class PaymentDto {
    private String impuid; // 거래 고유 번호
    private String nickname; // 사용자 닉네임
    private String status; // 결제 여부 paid = 1, 그 외 실패
    private Long amount; // 결제 금액
}
