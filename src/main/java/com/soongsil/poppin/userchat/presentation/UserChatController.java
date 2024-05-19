package com.soongsil.poppin.userchat.presentation;

import com.soongsil.poppin.global.response.ResponseDto;
import com.soongsil.poppin.userchat.application.UserChatSearchService;
import com.soongsil.poppin.userchat.application.request.UserChatRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="/v1/chat")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class UserChatController {
    private final UserChatSearchService userChatSearchService;

    // 유저 채팅 리스트 불러오기
    @GetMapping("/chat")
    public ResponseDto<Page<String[]>> getUserChatList(@ModelAttribute UserChatRequest request, Pageable pageable) {
        try {
            Long userId = request.getUserId();
            Page<String[]> userChatList = userChatSearchService.getUserChatList(userId, pageable);
            return ResponseDto.map(HttpStatus.OK.value(), "유저 채팅 리스트 불러오기 성공", userChatList);
        } catch (Exception ex) {
            Throwable targetException = ex.getCause();
            if (targetException != null) {
                System.out.println("원인 예외: " + targetException.getMessage());
            } else {
                System.out.println("예외 발생: " + ex.getMessage());
            }
            return ResponseDto.map(HttpStatus.NOT_FOUND.value(), "getUserChatList 에러 발생.", null);
        }
    }
}
