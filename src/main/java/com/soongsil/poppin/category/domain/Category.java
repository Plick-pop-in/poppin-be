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
    private boolean Fashion;

    // 뷰티
    @Column(name = "category_beauty", nullable = false)
    private boolean Beauty;

    // 음식
    @Column(name = "category_food", nullable = false)
    private boolean Food;

    // 연예
    @Column(name = "category_celeb", nullable = false)
    private boolean Celeb;

    // 캐릭터
    @Column(name = "category_charactor", nullable = false)
    private boolean Charactor;

    // 리빙
    @Column(name = "category_living", nullable = false)
    private boolean Living;

    // 가전/디지털
    @Column(name = "category_digital", nullable = false)
    private boolean Digital;

    @Builder
    public Category(boolean Fashion, boolean Beauty, boolean Food, boolean Celeb,
                    boolean Charactor, boolean Living, boolean Digital) {
        this.Fashion = Fashion;
        this.Beauty = Beauty;
        this.Food = Food;
        this.Celeb = Celeb;
        this.Charactor = Charactor;
        this.Living = Living;
        this.Digital = Digital;
    }
}
