package com.revature.PureDataBase2.services;

import java.util.List;
//import java.util.Optional;

import org.springframework.stereotype.Service;

import com.revature.PureDataBase2.repositories.ObjectTagRepository;
import com.revature.PureDataBase2.entities.ObjectTag;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ObjectTagService {
    private final ObjectTagRepository objectTagRepo;

    public List<ObjectTag> getAll() {
        return objectTagRepo.findAll();
    }

    // get all tags for an object
    /*
    public List<ObjectTag> getByLibraryNameAndObjectName(String libName, String objName) {
        return objectTagRepo.findByObjectLibraryNameAndObjectNameOrderByTagName(libName, objName);       
    }*/
}
