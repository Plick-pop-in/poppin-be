package com.soongsil.poppin.userchat.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserChatRepository extends JpaRepository<UserChat, Long> {

    // userId에 해당하는 채팅 리스트 불러오는 쿼리
    @Query("SELECT uc.popup.popupName " +
            "FROM UserChat uc " +
            "WHERE uc.member.userId = :userId")
    Page<UserChat> findUserChatsByMemberUserId(Long userId, Pageable pageable);
}
