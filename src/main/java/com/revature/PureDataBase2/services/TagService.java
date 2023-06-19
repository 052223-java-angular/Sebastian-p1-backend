package com.revature.PureDataBase2.services;

import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.revature.PureDataBase2.repositories.TagRepository;
import com.revature.PureDataBase2.repositories.PdObjectRepository;
import com.revature.PureDataBase2.entities.Tag;
import com.revature.PureDataBase2.entities.PdObject;
import com.revature.PureDataBase2.util.custom_exceptions.TagNotFoundException;

import lombok.AllArgsConstructor;

/**
 * The UserService class provides operations related to user management.
 */
@Service
@AllArgsConstructor
public class TagService {
    private final TagRepository tagRepo;
    private final PdObjectRepository objectRepo;
    // should I validate library name?

    public Tag getByName(String name) {
        Optional<Tag> tagOpt = tagRepo.findByNameIgnoreCase(name);

        if(tagOpt.isEmpty()) throw new TagNotFoundException("no tag found");
        return tagOpt.get();
    }

    public List<Tag> getByNames(Set<String> names) {
        return tagRepo.findByNameInOrderByName(names);
    }

    public boolean isValidTag(String name) {
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
        return tagRepo.findByNameIgnoreCase(name).isEmpty();
    }

    public List<Tag> getAll() {
        return tagRepo.findAll();
    }

    public List<String> getAllNames() {
        List<Tag> tags = tagRepo.findAllOrderByName();
        List<String> names = new ArrayList<String>();
        for(Tag tag : tags) {
            names.add(tag.getName());
        }
        return names;
    }
    
    public Tag create(Tag tag) {
        // save and return library
        return tagRepo.save(tag);
    }

    public List<PdObject> getObjectsByTagName(String tagName) {
        return objectRepo.findAllByObjectTagsTagNameOrderByName(tagName);
    }
}
