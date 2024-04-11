package com.soongsil.poppin.user.domain;

import com.soongsil.poppin.heart.domain.Heart;
import com.soongsil.poppin.userchat.domain.UserChat;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Entity
@Table(name="popin_user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

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

    @Column(name="created_date", nullable = false)
    private LocalDateTime createdDate;


    @OneToMany(mappedBy = "heart", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Heart> heart;

    @OneToMany(mappedBy = "userChat", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<UserChat> userChat;

    @Builder
    public User(String name, String email, String password, String nickName, Long point){
        this.name = name;
        this.email =email;
        this.password = password;
        this.nickName = nickName;
        this.point = point;
    }

    @PrePersist
    protected void onCreate() {
        createdDate = LocalDateTime.now();
    }
}
