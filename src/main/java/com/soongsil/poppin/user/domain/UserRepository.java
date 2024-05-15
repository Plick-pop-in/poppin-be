package com.soongsil.poppin.user.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Member, Long> {
    @Query("select m from Member m where m.email = :email")
    Member getWithEmail(@Param("email") String email);
}
