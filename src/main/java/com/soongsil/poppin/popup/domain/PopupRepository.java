package com.soongsil.poppin.popup.domain;

import com.soongsil.poppin.category.domain.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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
    //필터링 + 모든 기간 팝업 가져오는 쿼리
    @Query("SELECT DISTINCT p" +
            "FROM popup p" +
            "JOIN category c ON p.popupId = c.popup.popupId" +
            "WHERE p.category = :category"+
            "AND (:search is null OR p.name LIKE %:search%)")   //search 값 없으면 그냥 가져오고 있으면 해당 검색어로 가져오기
    List<Popup> findAllPopupsWithFilters(Category category, String search);

    //필터링 + 진행중인 팝업 가져오는 쿼리
    @Query("SELECT DISTINCT p " +
            "FROM Popup p " +
            "WHERE p.category = :category" +
            "JOIN category c ON p.popupId = c.popup.popupId" +
            "AND :currentDate BETWEEN p.popupStartDate AND p.popupEndDate "+
            "AND (:search is null OR p.name LIKE %:search%)")
    List<Popup> findOpenPopupsWithFilters(Category category, LocalDateTime currentDate, String search);

    //필터링 + 시작 전인 팝업 가져오는 쿼리
    @Query("SELECT DISTINCT p " +
            "FROM Popup p " +
            "WHERE p.category = :category" +
            "JOIN category c ON p.popupId = c.popup.popupId" +
            "AND :currentDate < p.popupStartDate"+
            "AND (:search is null OR p.name LIKE %:search%)")
    List<Popup> findWillPopupsWithFilters(Category category, LocalDateTime currentDate, String search);

    //필터링 + 종료된 팝업 가져오는 쿼리
    @Query("SELECT DISTINCT p " +
            "FROM Popup p " +
            "WHERE p.category = :category" +
            "JOIN category c ON p.popupId = c.popup.popupId" +
            "AND :currentDate > p.popupEndDate"+
            "AND (:search is null OR p.name LIKE %:search%)")
    List<Popup> findClosePopupsWithFilters(Category category, LocalDateTime currentDate, String search);
}