package com.revature.PureDataBase2.repositories;

import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.PureDataBase2.entities.Like;

/**
 * The UserRepository interface provides database operations for User entities.
 */

@Repository
public interface LikeRepository extends JpaRepository<Like, String> {
    List<Like> findByEntityTypeAndUserIdOrderByTimeDesc(Like.EntityType type, String userId);
    List<Like> findByEntityTypeAndEntityIdOrderByTimeDesc(Like.EntityType type, String dateId);
    Optional<Like> findByEntityIdAndUserId(String entityId, String userId);
    List<Like> findByUserIdOrderByTimeDesc(String userId);
}
