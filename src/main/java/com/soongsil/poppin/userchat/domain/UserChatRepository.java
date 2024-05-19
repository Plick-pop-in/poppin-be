package com.soongsil.poppin.userchat.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

@Repository
public interface UserChatRepository extends JpaRepository<UserChat, Long> {

    @Query("SELECT p.popupName " +
            "FROM Popup p " +
            "JOIN UserChat u ON u.popup = p " +
            "WHERE u.member.userId = :userId")

    Page<String[]> findUserChatListByUserId(@Param("userId") Long userId, Pageable pageable);
}

