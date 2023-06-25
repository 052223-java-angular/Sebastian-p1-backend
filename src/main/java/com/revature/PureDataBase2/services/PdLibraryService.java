package com.revature.PureDataBase2.services;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.revature.PureDataBase2.repositories.PdLibraryRepository;
import com.revature.PureDataBase2.repositories.PdObjectRepository;
import com.revature.PureDataBase2.entities.User;
import com.revature.PureDataBase2.entities.PdLibrary;
import com.revature.PureDataBase2.entities.PdObject;
import com.revature.PureDataBase2.entities.ObjectTag;
import com.revature.PureDataBase2.entities.LibraryTag;
import com.revature.PureDataBase2.entities.Tag;
import com.revature.PureDataBase2.entities.LTag;
import com.revature.PureDataBase2.DTO.requests.PdEditObject;
import com.revature.PureDataBase2.DTO.requests.PdEditLibrary;
import com.revature.PureDataBase2.util.custom_exceptions.LibraryNotFoundException;
import com.revature.PureDataBase2.util.custom_exceptions.ObjectNotFoundException;
import com.revature.PureDataBase2.util.custom_exceptions.ResourceConflictException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class PdLibraryService {
    private final PdLibraryRepository libraryRepo;
    private final PdObjectRepository objectRepo;
    private final TagService tagService;
    private final LTagService lTagService;
    private final Logger logger = LoggerFactory.getLogger(PdLibraryService.class);
    // should I validate library name?

    public PdLibraryService(PdLibraryRepository libraryRepo, PdObjectRepository objectRepo, TagService tagService,
        LTagService lTagService) {
        this.libraryRepo = libraryRepo;
        this.objectRepo = objectRepo;
        this.tagService = tagService;
        this.lTagService = lTagService;
    }
    
    public PdObject getObjectByObjectId(String objectId) {
        Optional<PdObject> optObj = objectRepo.findById(objectId);
        if(optObj.isEmpty()) throw new ObjectNotFoundException("object not found for Id " + objectId);
        return optObj.get();
    }

    public PdLibrary getById(String libraryId) {
        Optional<PdLibrary> optLib = libraryRepo.findById(libraryId);
        if(optLib.isEmpty()) throw new ObjectNotFoundException("library not found for Id " + libraryId);
        return optLib.get();
    }

    public List<PdLibrary> getAuthorLibsByNameLike(String authorName) {
        return libraryRepo.findByAuthorContainsIgnoreCase(authorName);
    }

    public List<PdObject> getAuthorObjsByNameLike(String authorName) {
        return objectRepo.findByAuthorContainsIgnoreCase(authorName);
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

        if(objOpt.isEmpty()) throw new ObjectNotFoundException("object " + name + " not found in library " +
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
    private final void mergeLibraryTags(Set<String> input, PdLibrary library) {
        Set<LibraryTag> libraryTags = library.getLibraryTags();
        List<LTag> remainderTags;
        List<LibraryTag> remainderLibraryTags = new ArrayList<LibraryTag>();
        String name;
        Iterator<LibraryTag> tagIterator = libraryTags.iterator();
        while(tagIterator.hasNext()) {
            name = tagIterator.next().getTag().getName();
            if(!input.contains(name)) {
                tagIterator.remove();
            } else {
                input.remove(name);
            }
        }
        remainderTags = lTagService.getByNames(input);
        for(LTag remainderTag : remainderTags) {
            remainderLibraryTags.add(new LibraryTag(library, remainderTag));
        }
        libraryTags.addAll(remainderLibraryTags);
    }

    @Transactional
    public PdLibrary updateLibrary(PdEditLibrary editLibrary, PdLibrary prevLibrary, User user) {

        String editString = editLibrary.getName();
        if(editString != null) {
            if(!isUnique(editString) && !editString.equals(prevLibrary.getName()))
                throw new ResourceConflictException("library " + editString + " already exists");
            prevLibrary.setName(editString);
        }
        logger.trace("updating library " + prevLibrary.getName());

        editString = editLibrary.getAuthor();
        if(editString != null) prevLibrary.setAuthor(editString);
        editString = editLibrary.getLibVersion();
        if(editString != null) prevLibrary.setRecentVersion(editString);
        editString = editLibrary.getDescription();
        if(editString != null) prevLibrary.setDescription(editString);

        Set<String> newTags = editLibrary.getLibraryTags();
        if(newTags != null) {
            logger.trace("merging/creating object tags for " + editLibrary.getName());
            mergeLibraryTags(newTags, prevLibrary);
        }
        prevLibrary.setLastEditedBy(user);

        return libraryRepo.save(prevLibrary);
    }


    // mutates existing
    private final void mergeObjectTags(Set<String> input, PdObject object) {
        Set<ObjectTag> objectTags = object.getObjectTags();
        List<Tag> remainderTags;
        List<ObjectTag> remainderObjectTags = new ArrayList<ObjectTag>();
        String name;
        Iterator<ObjectTag> tagIterator = objectTags.iterator();
        while(tagIterator.hasNext()) {
            name = tagIterator.next().getTag().getName();
            if(!input.contains(name)) {
                tagIterator.remove();
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

    @Transactional
    public PdObject newObject(PdEditObject editObject, User user, PdLibrary library) {
        String nameString = editObject.getName();
        logger.trace("creating object " + nameString);
        if(nameString == null) throw new RuntimeException("name was null");
        PdObject newObject = new PdObject(nameString, library, user);

        String editString = editObject.getAuthor();
        if(editString != null) newObject.setAuthor(editString);
        editString = editObject.getLibVersion();
        if(editString != null) newObject.setLibraryVersion(editString);
        editString = editObject.getDescription();
        if(editString != null) newObject.setDescription(editString);
        editString = editObject.getHelpText();
        if(editString != null) newObject.setHelpText(editString);

        Set<String> newTags = editObject.getObjectTags();
        // TODO: can probably just set the tags for new
        if(newTags != null) {
            logger.trace("merging/creating object tags for " + editObject.getName());
            mergeObjectTags(newTags, newObject);
        }

        return objectRepo.save(newObject);
    }

    @Transactional
    public PdObject updateObject(PdEditObject editObject, PdObject prevObject, User user,
        String libName) {
        PdLibrary library;

        String nameString = editObject.getName();
        String editString = editObject.getLibName();
        if(editString != null) {
            if(nameString == null) nameString = prevObject.getName();
            library = this.getByName(editString);
            if(isUniqueObjectName(nameString, editString)) {
                prevObject.setLibrary(library);
                prevObject.setName(nameString);
            } else if(!(editString.equals(prevObject.getLibrary().getName()) &&
                nameString.equals(prevObject.getName())))
                    throw new ResourceConflictException("object " + prevObject.getName() +
                        " already exists in library " + editString);
        } else if(nameString != null) {
            if(isUniqueObjectName(nameString, prevObject.getLibrary().getName())) {
                prevObject.setName(nameString);
            } else throw new ResourceConflictException("object " + prevObject.getName() +
                " already exists in library " + editString);
        }

        editString = editObject.getAuthor();
        if(editString != null) prevObject.setAuthor(editString);
        editString = editObject.getLibVersion();
        if(editString != null) prevObject.setLibraryVersion(editString);
        editString = editObject.getDescription();
        if(editString != null) prevObject.setDescription(editString);
        editString = editObject.getHelpText();
        if(editString != null) prevObject.setHelpText(editString);

        Set<String> newTags = editObject.getObjectTags();
        if(newTags != null) {
            logger.trace("merging/creating object tags for " + prevObject.getName());
            mergeObjectTags(newTags, prevObject);
        }
        prevObject.setLastEditedBy(user);


        return objectRepo.save(prevObject);
    }

    public PdLibrary create(PdEditLibrary editLib, User user) {
        // create new library
        logger.trace("creating library " + editLib.getName());
        PdLibrary library = new PdLibrary(editLib.getName(), user);
        library.setLastEditedBy(user);

        String editString = editLib.getAuthor();
        if(editString != null) library.setAuthor(editString);
        editString = editLib.getLibVersion();
        if(editString != null) library.setRecentVersion(editString);
        editString = editLib.getDescription();
        if(editString != null) library.setDescription(editString);

        Set<String> newTags = editLib.getLibraryTags();
        // TODO: can probably just set the tags for new
        if(newTags != null) {
            logger.trace("merging/creating library tags for " + editLib.getName());
            mergeLibraryTags(newTags, library);
        }

        // save and return library
        return libraryRepo.save(library);
    }

}
