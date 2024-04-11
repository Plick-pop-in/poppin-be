package com.soongsil.poppin.userchat.domain;

import com.soongsil.poppin.popup.domain.Popup;
import com.soongsil.poppin.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "popin_userchat")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserChat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="uchat_id")
    private Long uchatId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="popup_id", nullable = false)
    private Popup popup;

    @Column(name="uchat_price", nullable = false)
    private Integer price;

    @Column(name="created_date", nullable = false)
    private LocalDateTime date;

    @Builder
    public UserChat(User user, Popup popup, Integer price){
        this.user = user;
        this.popup = popup;
        this.price = price;
    }

}
