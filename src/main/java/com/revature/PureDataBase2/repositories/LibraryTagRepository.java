
package com.revature.PureDataBase2.repositories;

import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.PureDataBase2.entities.LibraryTag;
import com.revature.PureDataBase2.entities.PdLibrary;
import com.revature.PureDataBase2.entities.LTag;

/**
 * The UserRepository interface provides database operations for User entities.
 */

@Repository
public interface LibraryTagRepository extends JpaRepository<LibraryTag, String> {
    Optional<LibraryTag> findByLibraryAndTag(PdLibrary object, LTag lTag);
    List<LibraryTag> findByLibrary(PdLibrary object);
    List<LibraryTag> findByTag(LTag lTtag);
    List<LibraryTag> findByTagName(String lTagName);
}
