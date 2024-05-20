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
            "FROM UserChat uc " +
            "JOIN Popup p ON uc.popup.popupId = p.popupId " +
            "WHERE uc.member.userId = :userId " +
            "AND uc.popup.popupId IN ( " +
            "    SELECT uc2.popup.popupId " +
            "    FROM UserChat uc2 " +
            "    WHERE uc2.member.userId = :userId)")

    Page<String[]> findUserChatListByUserId(@Param("userId") Long userId, Pageable pageable);
}

