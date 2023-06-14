package com.revature.PureDataBase2.repositories;

import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.PureDataBase2.entities.PdObject;

/**
 * The UserRepository interface provides database operations for User entities.
 */

@Repository
public interface PdObjectRepository extends JpaRepository<PdObject, String> {
    void deleteByNameAndLibraryName(String name, String libName);
    Optional<PdObject> findByNameAndLibraryName(String name, String libName);
    List<PdObject> findByNameContainsIgnoreCaseOrderByName(String name);
    List<PdObject> findAllByObjectTagsTagNameOrderByName(String tagName);
    List<PdObject> findByAuthorContainsIgnoreCase(String authorName);
}
