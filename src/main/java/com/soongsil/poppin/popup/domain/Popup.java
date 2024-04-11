package com.soongsil.poppin.popup.domain;

import com.soongsil.poppin.category.domain.Category;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Entity
@Table(name = "popin_popup")
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

    // 팝업 소개 속성
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
    private LocalDateTime date;

    @OneToOne(mappedBy = "popup")
    private Category category;

    @OneToMany(mappedBy = "popup", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<PopupImage> popupImages;

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
        date = LocalDateTime.now();
    }
}
