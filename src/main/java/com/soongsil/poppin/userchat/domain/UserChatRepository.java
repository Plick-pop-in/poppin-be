package com.soongsil.poppin.userchat.domain;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserChatRepository extends JpaRepository<UserChat, Long> {

    // userId에 해당하는 채팅 리스트 불러오는 쿼리
    @Query("SELECT uc.popup.popupName " +
            "FROM UserChat uc " +
            "WHERE uc.member.userId = :userId")
    List<UserChat> findUserChatsByMemberUserId(Long userId);

    //userId와 해당하는 팝업에 있는 채팅 리스트인지
    @Query("SELECT COUNT(uc) > 0 FROM UserChat uc " +
            "WHERE (uc.member.userId = :userId  " +
            "AND uc.popup.id = :popupId ) ")
    Boolean existsByMemberUserIdAndPopupPopupId(Long userId, Long popupId);

}
