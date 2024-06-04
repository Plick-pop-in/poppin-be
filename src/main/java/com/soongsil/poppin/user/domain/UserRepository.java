package com.soongsil.poppin.user.domain;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Member, Long> {
    @Query("select m from Member m where m.email = :email")
    Member getWithEmail(@Param("email") String email);

    Member findBynickName(String nickName);

    @Query("select m.point from Member m where m.userId = :userId")
    Long getPointById(@Param("userId") Long userId);


    @Modifying
    @Transactional
    @Query("update Member m set m.point = :point where m.userId = :userid")
    void updatePayPoint(@Param("userId") Long userid, @Param("point") Long point);
}
