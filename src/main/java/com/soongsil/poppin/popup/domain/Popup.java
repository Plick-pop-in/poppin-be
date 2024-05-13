package com.soongsil.poppin.popup.domain;

import com.soongsil.poppin.category.domain.Category;
import com.soongsil.poppin.heart.domain.Heart;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Entity
@Table(name = "popup")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Popup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "popup_id")
    private Long popupId;

    @Column(name = "popup_name", nullable = false)
    private String popupName;

    @Column(name = "popup_time", nullable = false)
    private String popupTime;

    @Column(name = "popup_intro", nullable = false)
    private String popupIntro;

    @Column(name = "popup_page_link")
    private String popupPageLink;

    @Column(name = "popup_location")
    private String popupLocation;

    @Column(name = "popup_city")
    private String popupCity;

    @Column(name = "popup_start_date", nullable = false)
    private LocalDateTime popupStartDate;

    @Column(name = "popup_end_date", nullable = false)
    private LocalDateTime popupEndDate;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @OneToOne(mappedBy = "popup", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Category category;

    @OneToMany(mappedBy = "popup", cascade = CascadeType.ALL)
    private List<PopupImage> popupImages;

    @OneToMany(mappedBy = "popup", cascade = CascadeType.ALL)
    private List<Heart> hearts; // 양방향 관계 설정

    @Builder
    public Popup(String popupName, String popupTime, String popupIntro, LocalDateTime popupStartDate, LocalDateTime popupEndDate) {
        this.popupName = popupName;
        this.popupTime = popupTime;
        this.popupIntro = popupIntro;
        this.popupStartDate = popupStartDate;
        this.popupEndDate = popupEndDate;
    }

    @PrePersist
    protected void onCreate() {
        createdDate = LocalDateTime.now();
    }
}
