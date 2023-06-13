package com.revature.PureDataBase2.services;

import java.util.Set;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.revature.PureDataBase2.repositories.LTagRepository;
import com.revature.PureDataBase2.repositories.PdLibraryRepository;
import com.revature.PureDataBase2.entities.LTag;
import com.revature.PureDataBase2.entities.PdLibrary;
import com.revature.PureDataBase2.util.custom_exceptions.TagNotFoundException;

import lombok.AllArgsConstructor;

/**
 * The UserService class provides operations related to user management.
 */
@Service
@AllArgsConstructor
public class LTagService {
    private final LTagRepository lTagRepo;
    private final PdLibraryRepository libraryRepo;
    // should I validate library name?

    public LTag getByName(String name) {
        Optional<LTag> lTagOpt = lTagRepo.findByNameIgnoreCase(name);

        if(lTagOpt.isEmpty()) throw new TagNotFoundException("no library tag found");
        return lTagOpt.get();
    }

    public List<LTag> getByNames(Set<String> names) {
        return lTagRepo.findByNameInOrderByName(names);
    }

    public boolean isValidLTag(String name) {
        char c;
        if(name.length() == 0) return false;
        for(int i = 0; i < name.length(); i++) {
            c = name.charAt(i);
            if(c < 'a' || c > 'z')
                if(c != '-') return false;
        }
        return true;
    }

    public boolean isUnique(String name) {
        return lTagRepo.findByNameIgnoreCase(name).isEmpty();
    }

    public List<LTag> getAll() {
        return lTagRepo.findAll();
    }

    public LTag create(LTag lTag) {
        // save and return library
        return lTagRepo.save(lTag);
    }

    public List<PdLibrary> getLibrariesByTagName(String lTagName) {
        return libraryRepo.findAllByLibraryTagsTagNameOrderByName(lTagName);
    }
}
