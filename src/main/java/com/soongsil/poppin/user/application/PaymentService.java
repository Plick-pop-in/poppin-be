package com.soongsil.poppin.user.application;

import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import com.soongsil.poppin.global.response.ErrorCode;
import com.soongsil.poppin.user.application.exception.UserException;
import com.soongsil.poppin.user.application.response.PaymentDto;
import com.soongsil.poppin.user.domain.Member;
import com.soongsil.poppin.user.domain.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.io.IOException;

@Service
@Transactional
@Log4j2
public class PaymentService {
    private final IamportClient iamportClient;
    private final UserRepository userRepository;

    public PaymentService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.iamportClient = new IamportClient("8754401450070487", "oU0hOQPSSuFLK6qSfLZZRrprvF3pqz3K0OOniDSvrS58RfR6sPFZnhXhVt7Av9AVPtPqItaiuL43BLR1");
    }

    public PaymentDto verifyPayment(String imp_uid) throws IamportResponseException, IOException {
        IamportResponse<Payment> iamportResponse = iamportClient.paymentByImpUid(imp_uid); // 결제 검증 시작
        Long amount = (iamportResponse.getResponse().getAmount()).longValue(); // 결제 금액
        String nickname = iamportResponse.getResponse().getBuyerName(); // 유저 닉네임
        String status = iamportResponse.getResponse().getStatus(); // Paid 이면 1

        Member result = userRepository.findBynickName(nickname);
        if (result == null) {
            throw new UserException(ErrorCode.USER_NOT_FOUND);
        }

        Long newPoint = result.getPoint() + amount;
        result.setPoint(newPoint);

        userRepository.save(result);

        PaymentDto paymentDto = PaymentDto.builder()
                .impuid(imp_uid)
                .amount(newPoint)
                .nickname(nickname)
                .status(amount + "포인트가 충전되었습니다.")
                .build();

        return paymentDto;
    }
}