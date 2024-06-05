package com.soongsil.poppin.user.domain;

import com.soongsil.poppin.heart.domain.Heart;
import com.soongsil.poppin.userchat.domain.UserChat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name="user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "user_name")
    private String name;

    @Column(name="user_email", unique = true, nullable = false)
    private String email;

    @Column(name = "user_password", nullable = false)
    private String password;

    @Column(name="user_nickname", unique = true, nullable = false)
    private String nickName;

    @Column(name = "user_point")
    private Long point;

    @Column(name = "user_social")
    private boolean social;

    @Column(name="created_date", nullable = false)
    private LocalDateTime createdDate;


    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Heart> heart;

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<UserChat> userChat;

    @Builder
    public Member(String name, String email, String password, String nickName, boolean social, Long point){
        this.name = name;
        this.email =email;
        this.password = password;
        this.nickName = nickName;
        this.social = social;
        this.point = point;
    }

    @PrePersist
    protected void onCreate() {
        createdDate = LocalDateTime.now();
    }
}
