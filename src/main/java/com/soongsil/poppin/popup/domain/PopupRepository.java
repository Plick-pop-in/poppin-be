package com.soongsil.poppin.popup.domain;

import com.soongsil.poppin.category.domain.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PopupRepository extends JpaRepository<Popup, Long> {

    // 좋아요가 가장 많은 상위 3개 팝업 ID 가져오기
    @Query("SELECT p.popupId " +
            "FROM Popup p " +
            "JOIN Heart h ON p.popupId = h.popup.popupId " +
            "GROUP BY p.popupId " +
            "ORDER BY COUNT(h) DESC " +
            "LIMIT 3")
    List<Long> findTop3PopupIdsWithMostLikes();

    // 진행 중인 팝업 조회
    @Query("SELECT p " +
            "FROM Popup p " +
            "WHERE :currentDate BETWEEN p.popupStartDate AND p.popupEndDate")
    List<Popup> findInProgressPopups(LocalDateTime currentDate, Pageable pageable);


    // 메인페이지 좋아요 top3 가져오는 쿼리
    @Query("SELECT p.popupId, i.popupImageUrl, p.popupName, " +
            "CONCAT(FUNCTION('DATE_FORMAT', p.popupStartDate, '%Y-%m-%d'), ' - ', FUNCTION('DATE_FORMAT', p.popupEndDate, '%Y-%m-%d')) AS popupPeriod, " +
            "(SELECT COUNT(*) FROM Heart h WHERE h.popup = p) AS likeCount " +
            "FROM Popup p " +
            "LEFT JOIN p.popupImages i " +
            "GROUP BY p.popupId, i.popupImageUrl, p.popupName, p.popupStartDate, p.popupEndDate ")
    Page<Object[]> findTop3ByOrderByLikeCountDesc(Pageable pageable);

    //id에 해당하는 값 가져오는 쿼리
    @Query(value = "SELECT * FROM popup WHERE popup_id = :popupId", nativeQuery = true)
    Popup findPopupById(Long popupId);


    /*팝업 필터링 쿼리*/

    // 필터링 + 모든 기간 팝업 가져오는 쿼리
    @Query("SELECT DISTINCT p " +
            "FROM Popup p " +
            "JOIN p.category c " + // Popup 엔티티와 Category 엔티티의 조인
            "WHERE (c.fashion = true AND :fashion = true) OR " +
            "(c.beauty = true AND :beauty = true) OR " +
            "(c.food = true AND :food = true) OR " +
            "(c.celeb = true AND :celeb = true) OR " +
            "(c.charactor = true AND :charactor = true) OR " +
            "(c.living = true AND :living = true) OR " +
            "(c.digital = true AND :digital = true) OR " +
            "(c.game = true AND :game = true) " +
            "AND (:search is null OR p.popupName LIKE %:search%)")
    // 검색어가 있으면 검색어에 맞는 팝업만 선택
    List<Popup> findAllPopupsWithFilters(boolean fashion, boolean beauty, boolean food, boolean celeb,
                                         boolean charactor, boolean living,boolean digital, boolean game,
                                         String search);

    // 필터링 + 진행중인 팝업 가져오는 쿼리
    @Query("SELECT DISTINCT p " +
            "FROM Popup p " +
            "JOIN p.category c " + // Popup 엔티티와 Category 엔티티의 조인
            "WHERE (c.fashion = true AND :fashion = true) OR " +
            "(c.beauty = true AND :beauty = true) OR " +
            "(c.food = true AND :food = true) OR " +
            "(c.celeb = true AND :celeb = true) OR " +
            "(c.charactor = true AND :charactor = true) OR " +
            "(c.living = true AND :living = true) OR " +
            "(c.digital = true AND :digital = true) OR " +
            "(c.game = true AND :game = true) " +
            "AND :currentDate BETWEEN p.popupStartDate AND p.popupEndDate " + // 현재 날짜가 시작일과 종료일 사이에 있는 경우
            "AND (:search is null OR p.popupName LIKE %:search%)")
    // 검색어가 있으면 검색어에 맞는 팝업만 선택
    List<Popup> findOpenPopupsWithFilters(boolean fashion, boolean beauty, boolean food, boolean celeb,
                                          boolean charactor, boolean living,boolean digital, boolean game,
                                          LocalDateTime currentDate, String search);

    // 필터링 + 시작 전인 팝업 가져오는 쿼리
    @Query("SELECT DISTINCT p " +
            "FROM Popup p " +
            "JOIN p.category c " + // Popup 엔티티와 Category 엔티티의 조인
            "WHERE (c.fashion = true AND :fashion = true) OR " +
            "(c.beauty = true AND :beauty = true) OR " +
            "(c.food = true AND :food = true) OR " +
            "(c.celeb = true AND :celeb = true) OR " +
            "(c.charactor = true AND :charactor = true) OR " +
            "(c.living = true AND :living = true) OR " +
            "(c.digital = true AND :digital = true) OR " +
            "(c.game = true AND :game = true) " +
            "AND :currentDate < p.popupStartDate " + // 현재 날짜가 시작일 이전인 경우
            "AND (:search is null OR p.popupName LIKE %:search%)")
    // 검색어가 있으면 검색어에 맞는 팝업만 선택
    List<Popup> findWillPopupsWithFilters(boolean fashion, boolean beauty, boolean food, boolean celeb,
                                          boolean charactor, boolean living,boolean digital, boolean game,
                                          LocalDateTime currentDate, String search);

    // 필터링 + 종료된 팝업 가져오는 쿼리
    @Query("SELECT DISTINCT p " +
            "FROM Popup p " +
            "JOIN p.category c " + // Popup 엔티티와 Category 엔티티의 조인
            "WHERE (c.fashion = true AND :fashion = true) OR " +
            "(c.beauty = true AND :beauty = true) OR " +
            "(c.food = true AND :food = true) OR " +
            "(c.celeb = true AND :celeb = true) OR " +
            "(c.charactor = true AND :charactor = true) OR " +
            "(c.living = true AND :living = true) OR " +
            "(c.digital = true AND :digital = true) OR " +
            "(c.game = true AND :game = true) " +
            "AND :currentDate > p.popupEndDate " + // 현재 날짜가 종료일 이후인 경우
            "AND (:search is null OR p.popupName LIKE %:search%)")
    // 검색어가 있으면 검색어에 맞는 팝업만 선택
    List<Popup> findClosePopupsWithFilters(boolean fashion, boolean beauty, boolean food, boolean celeb,
                                           boolean charactor, boolean living,boolean digital, boolean game,
                                           LocalDateTime currentDate, String search);
}