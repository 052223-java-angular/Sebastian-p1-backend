package com.revature.PureDataBase2.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.revature.PureDataBase2.repositories.PdLibraryRepository;
import com.revature.PureDataBase2.entities.User;
import com.revature.PureDataBase2.entities.PdLibrary;
import com.revature.PureDataBase2.util.custom_exceptions.LibraryNotFoundException;

import lombok.AllArgsConstructor;

/**
 * The UserService class provides operations related to user management.
 */
@Service
@AllArgsConstructor
public class PdLibraryService {
    private final PdLibraryRepository libraryRepo;
    // should I validate library name?

    public PdLibrary getByName(String name) {
        Optional<PdLibrary> libOpt = libraryRepo.findByName(name);

        if(libOpt.isEmpty()) throw new LibraryNotFoundException("no library found");
        return libOpt.get();
    }

    public boolean isUnique(String name) {
        return libraryRepo.findByName(name).isEmpty();
    }

    public PdLibrary update(PdLibrary library) {
        return libraryRepo.save(library);
    }

    public List<PdLibrary> getAll() {
        return libraryRepo.findAll();
    }

    public PdLibrary create(PdLibrary library, User user) {
        // create new library
        library.setLastEditedBy(user);

        // save and return library
        return libraryRepo.save(library);
    }
}
