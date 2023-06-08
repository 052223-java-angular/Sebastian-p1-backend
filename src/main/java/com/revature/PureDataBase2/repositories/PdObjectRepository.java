package com.revature.PureDataBase2.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.PureDataBase2.entities.PdObject;
import com.revature.PureDataBase2.entities.PdLibrary;

/**
 * The UserRepository interface provides database operations for User entities.
 */

@Repository
public interface PdObjectRepository extends JpaRepository<PdObject, String> {

    Optional<PdObject> findByNameAndLibrary(String name, PdLibrary library);
}
