package com.revature.PureDataBase2.repositories;

import java.util.Optional;
import java.util.Set;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.PureDataBase2.entities.LTag;
import com.revature.PureDataBase2.entities.PdLibrary;

/**
 * The UserRepository interface provides database operations for User entities.
 */

@Repository
public interface LTagRepository extends JpaRepository<LTag, String> {
    Optional<LTag> findByNameIgnoreCase(String name);
    List<LTag> findByNameInOrderByName(Set<String> names);
    List<LTag> findByLibraryTagsLibrary(PdLibrary object);
    List<LTag> findAllByOrderByName();
}
