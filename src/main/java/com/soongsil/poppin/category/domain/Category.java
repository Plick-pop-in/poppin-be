package com.soongsil.poppin.category.domain;

import com.soongsil.poppin.popup.domain.Popup;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "popin_popup_category")
@NoArgsConstructor
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
    private boolean categoryFashion;

    // 뷰티
    @Column(name = "category_beauty", nullable = false)
    private boolean categoryBeauty;

    // 음식
    @Column(name = "category_food", nullable = false)
    private boolean categoryFood;

    // 연예
    @Column(name = "category_celeb", nullable = false)
    private boolean categoryCeleb;

    // 캐릭터
    @Column(name = "category_charactor", nullable = false)
    private boolean categoryCharactor;

    // 리빙
    @Column(name = "category_living", nullable = false)
    private boolean categoryLiving;

    // 가전/디지털
    @Column(name = "category_digital", nullable = false)
    private boolean categoryDigital;

    @Builder
    public Category(boolean categoryFashion, boolean categoryBeauty, boolean categoryFood, boolean categoryCeleb,
                    boolean categoryCharactor, boolean categoryLiving, boolean categoryDigital) {
        this.categoryFashion = categoryFashion;
        this.categoryBeauty = categoryBeauty;
        this.categoryFood = categoryFood;
        this.categoryCeleb = categoryCeleb;
        this.categoryCharactor = categoryCharactor;
        this.categoryLiving = categoryLiving;
        this.categoryDigital = categoryDigital;
    }
}
