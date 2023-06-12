package com.revature.PureDataBase2.repositories;

import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.PureDataBase2.entities.PdLibrary;

/**
 * The UserRepository interface provides database operations for User entities.
 */

@Repository
public interface PdLibraryRepository extends JpaRepository<PdLibrary, String> {
    Optional<PdLibrary> findByName(String name);
    List<PdLibrary> findByNameContainsIgnoreCase(String name);
    List<PdLibrary> findByAuthorContainsIgnoreCase(String author);
}
