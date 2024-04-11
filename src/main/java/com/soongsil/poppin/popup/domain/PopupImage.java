package com.soongsil.poppin.popup.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "popin_popupImg")
@NoArgsConstructor
public class PopupImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "popup_image_id")
    private Long popupImageId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "popup_id", nullable = false)
    private Popup popup;

    @Column(name = "popup_image_url", nullable = false)
    private String popupImageUrl;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Builder
    public PopupImage(Popup popup, String popupImageUrl) {
        this.popup = popup;
        this.popupImageUrl = popupImageUrl;
    }

    @PrePersist
    protected void onCreate() {
        createdDate = LocalDateTime.now();
    }
}