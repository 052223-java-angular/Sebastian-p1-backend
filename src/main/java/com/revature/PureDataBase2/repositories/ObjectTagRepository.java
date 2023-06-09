package com.revature.PureDataBase2.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.PureDataBase2.entities.ObjectTag;
import com.revature.PureDataBase2.entities.PdObject;
import com.revature.PureDataBase2.entities.Tag;

/**
 * The UserRepository interface provides database operations for User entities.
 */

@Repository
public interface ObjectTagRepository extends JpaRepository<ObjectTag, String> {
    Optional<ObjectTag> findByObjectAndTag(PdObject object, Tag tag);
}
