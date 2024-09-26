package com.apparel.apparelmanagement.Repository;

import com.apparel.apparelmanagement.Model.Apparel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ApparelRepo extends JpaRepository<Apparel, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE Apparel a SET a.name = :name, a.type = :type, a.category = :category, a.username = :username, a.status = :status WHERE a.id = :id")
    void updateApparel(@Param("id") Long id, @Param("name") String name, @Param("type") String type, @Param("category") String category, @Param("username") String username, @Param("status") String status);

    List<Apparel> findByUsername(String username);

    List<Apparel> findByCategory(String category);

    List<Apparel> findByType(String type);

    List<Apparel> findByStatus(String status);

    List<Apparel> findByUsernameAndNameContaining(String whoIsLoggedIn, String search);
}