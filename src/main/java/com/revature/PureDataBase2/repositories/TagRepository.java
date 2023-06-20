package com.revature.PureDataBase2.repositories;

import java.util.Optional;
import java.util.Set;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.PureDataBase2.entities.Tag;
import com.revature.PureDataBase2.entities.PdObject;

/**
 * The UserRepository interface provides database operations for User entities.
 */

@Repository
public interface TagRepository extends JpaRepository<Tag, String> {
    Optional<Tag> findByNameIgnoreCase(String name);
    List<Tag> findByNameInOrderByName(Set<String> names);
    List<Tag> findByObjectTagsObject(PdObject object);
    List<Tag> findAllByOrderByName();
}
