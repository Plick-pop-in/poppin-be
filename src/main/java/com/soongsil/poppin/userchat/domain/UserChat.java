package com.soongsil.poppin.userchat.domain;

import com.soongsil.poppin.popup.domain.Popup;
import com.soongsil.poppin.user.domain.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "chat")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserChat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="uchat_id")
    private Long uchatId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="popup_id", nullable = false)
    private Popup popup;

    @Column(name="uchat_price", nullable = false)
    private Integer price;

    @Column(name="created_date", nullable = false)
    private LocalDateTime createdDate;

    @Builder
    public UserChat(Member member, Popup popup, Integer price){
        this.member = member;
        this.popup = popup;
        this.price = price;
    }

    @PrePersist
    protected void onCreate() {
        createdDate = LocalDateTime.now();
    }
}
