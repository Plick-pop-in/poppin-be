package com.soongsil.poppin.category.domain;

import com.soongsil.poppin.popup.domain.Popup;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "popin_popup_category")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long categoryId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "popup_id", nullable = false)
    private Popup popup;

    // 패션
    @Column(name = "category_fashion", nullable = false)
    private boolean fashion;

    // 뷰티
    @Column(name = "category_beauty", nullable = false)
    private boolean beauty;

    // 음식
    @Column(name = "category_food", nullable = false)
    private boolean food;

    // 연예
    @Column(name = "category_celeb", nullable = false)
    private boolean celeb;

    // 캐릭터
    @Column(name = "category_charactor", nullable = false)
    private boolean charactor;

    // 리빙
    @Column(name = "category_living", nullable = false)
    private boolean living;

    // 가전/디지털
    @Column(name = "category_digital", nullable = false)
    private boolean digital;

    @Builder
    public Category(boolean fashion, boolean beauty, boolean food, boolean celeb,
                    boolean charactor, boolean living, boolean digital) {
        this.fashion = fashion;
        this.beauty = beauty;
        this.food = food;
        this.celeb = celeb;
        this.charactor = charactor;
        this.living = living;
        this.digital = digital;
    }
}
