package com.revature.PureDataBase2.services;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.revature.PureDataBase2.repositories.PdLibraryRepository;
import com.revature.PureDataBase2.repositories.PdObjectRepository;
import com.revature.PureDataBase2.entities.User;
import com.revature.PureDataBase2.entities.PdLibrary;
import com.revature.PureDataBase2.entities.PdObject;
import com.revature.PureDataBase2.entities.ObjectTag;
import com.revature.PureDataBase2.entities.Tag;
import com.revature.PureDataBase2.DTO.requests.PdEditObject;
import com.revature.PureDataBase2.util.custom_exceptions.LibraryNotFoundException;
import com.revature.PureDataBase2.util.custom_exceptions.ObjectNotFoundException;
import com.revature.PureDataBase2.util.custom_exceptions.ResourceConflictException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PdLibraryService {
    private final PdLibraryRepository libraryRepo;
    private final PdObjectRepository objectRepo;
    private final TagService tagService;
    // should I validate library name?

    public List<PdLibrary> getAuthorLibsByNameLike(String authorName) {
        return libraryRepo.findByAuthorContainsIgnoreCase(authorName);
    }

    public List<PdLibrary> getByNameLike(String libName) {
        return libraryRepo.findByNameContainsIgnoreCase(libName);
    }

    public List<PdObject> getObjectByNameLike(String objectName) {
        return objectRepo.findByNameContainsIgnoreCaseOrderByName(objectName);
    }

    public PdLibrary getByName(String name) {
        Optional<PdLibrary> libOpt = libraryRepo.findByName(name);

        if(libOpt.isEmpty()) throw new LibraryNotFoundException("no library found");
        return libOpt.get();
    }

    public PdObject getObjectByNameAndLibraryName(String name, String libName) {
        Optional<PdObject> objOpt = objectRepo.findByNameAndLibraryName(name, libName);

        if(objOpt.isEmpty()) throw new ObjectNotFoundException("object " + name + " not found in library" +
            libName);
        return objOpt.get();
    }

    public void deleteObjectByNameAndLibraryName(String name, String libName) {
        objectRepo.deleteByNameAndLibraryName(name, libName);
    }

    public boolean isUnique(String name) {
        return libraryRepo.findByName(name).isEmpty();
    }

    public boolean isUniqueObjectName(String name, String libName) {
        return objectRepo.findByNameAndLibraryName(name, libName).isEmpty();
    }

    public PdLibrary update(PdLibrary library) {
        return libraryRepo.save(library);
    }

    public List<PdLibrary> getAll() {
        return libraryRepo.findAll();
    }

    public PdObject saveObject(PdObject object, User user) {
        object.setLastEditedBy(user);

        return objectRepo.save(object);
    }
    
    // mutates existing
    private final void mergeTags(Set<String> input, PdObject object) {
        Set<ObjectTag> objectTags = object.getObjectTags();
        List<Tag> remainderTags;
        List<ObjectTag> remainderObjectTags = new ArrayList<ObjectTag>();
        String name;
        for(ObjectTag objectTag : objectTags) {
            name = objectTag.getTag().getName();
            if(!input.contains(name)) {
                objectTags.remove(objectTag);
            } else {
                input.remove(name);
            }
        }
        remainderTags = tagService.getByNames(input);
        for(Tag remainderTag : remainderTags) {
            remainderObjectTags.add(new ObjectTag(object, remainderTag));
        }
        objectTags.addAll(remainderObjectTags);
    }

    public PdObject updateObject(PdEditObject editObject, PdObject prevObject, User user,
        String libName) {
        PdLibrary library;

        String editString;
        editString = editObject.getName();
        if(editString != null) prevObject.setName(editString);
        editString = editObject.getAuthor();
        if(editString != null) prevObject.setAuthor(editString);
        editString = editObject.getLibVersion();
        if(editString != null) prevObject.setLibraryVersion(editString);
        editString = editObject.getDescription();
        if(editString != null) prevObject.setDescription(editString);
        editString = editObject.getLibName();
        if(editString != null)
            if(!editString.equals(libName)) {
                library = this.getByName(editString);
                if(isUniqueObjectName(prevObject.getName(), editString))
                    prevObject.setLibrary(library);
                else throw new ResourceConflictException("object " + prevObject.getName() +
                    " already exists in library " + editString);
            }
        Set<String> newTags = editObject.getObjectTags();
        if(newTags != null) {
            mergeTags(newTags, prevObject);
        }
        prevObject.setLastEditedBy(user);

        return objectRepo.save(prevObject);
    }

    public PdLibrary create(PdLibrary library, User user) {
        // create new library
        library.setLastEditedBy(user);

        // save and return library
        return libraryRepo.save(library);
    }

}
