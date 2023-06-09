package com.revature.PureDataBase2.services;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.revature.PureDataBase2.DTO.requests.ObjAddress;
import com.revature.PureDataBase2.repositories.TagRepository;
import com.revature.PureDataBase2.repositories.ObjectTagRepository;
import com.revature.PureDataBase2.services.PdLibraryService;
import com.revature.PureDataBase2.entities.Tag;
import com.revature.PureDataBase2.entities.PdObject;
import com.revature.PureDataBase2.entities.ObjectTag;
import com.revature.PureDataBase2.util.custom_exceptions.TagNotFoundException;

import lombok.AllArgsConstructor;

/**
 * The UserService class provides operations related to user management.
 */
@Service
@AllArgsConstructor
public class TagService {
    private final TagRepository tagRepo;
    private final ObjectTagRepository objectTagRepo;
    private final PdLibraryService pdLibraryService;
    // should I validate library name?

    public void editForObject(ObjAddress objAddress, String tagName) {
        Optional<Tag> tagOpt = tagRepo.findByName(tagName);
        Tag tag;
        Optional<ObjectTag> optObjectTag;
        ObjectTag objectTag;
        //null means not in the json, (not false)
        PdObject object = pdLibraryService.getObjectByNameAndLibraryName(objAddress.getObjectName(),
            objAddress.getLibraryName());
        // add changes
        if(objAddress.isPresent() != false) {
            if(tagOpt.isEmpty()) {
                tag = new Tag(tagName);
                tagRepo.save(tag);
            } else tag = tagOpt.get();
            optObjectTag = objectTagRepo.findByObjectAndTag(object, tag);
            if(optObjectTag.isEmpty()) {
                objectTag = new ObjectTag(object, tag);
                objectTagRepo.save(objectTag);
            }
            objAddress.setPresent(true);
        } else { // delete
            optObjectTag = objectTagRepo.findByObjectAndTag(object, tagOpt.get());
            if(!optObjectTag.isEmpty()) {
                objectTagRepo.delete(optObjectTag.get());
            }
            objAddress.setPresent(false);
        }
    }

    public Tag getByName(String name) {
        Optional<Tag> tagOpt = tagRepo.findByName(name);

        if(tagOpt.isEmpty()) throw new TagNotFoundException("no library found");
        return tagOpt.get();
    }

    public boolean isValidTag(String name) {
        char c;
        for(int i = 0; i < name.length(); i++) {
            c = name.charAt(i);
            if(c < 'a' || c > 'z')
                if(c != '-') return false;
        }
        return true;
    }

    public boolean isUnique(String name) {
        return tagRepo.findByName(name).isEmpty();
    }

    public List<Tag> getAll() {
        return tagRepo.findAll();
    }

    public Tag create(Tag tag) {
        // save and return library
        return tagRepo.save(tag);
    }
}
