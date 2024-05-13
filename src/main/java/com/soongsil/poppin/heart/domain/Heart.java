package com.soongsil.poppin.heart.domain;

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
@Table(name = "heart")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Heart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "heart_id")
    private Long heartId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "popup_id", nullable = false)
    private Popup popup;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    @Builder
    public Heart(User user, Popup popup) {
        this.user = user;
        this.popup = popup;
    }

    @PrePersist
    protected void onCreate() {
        createdDate = LocalDateTime.now();
    }
}
