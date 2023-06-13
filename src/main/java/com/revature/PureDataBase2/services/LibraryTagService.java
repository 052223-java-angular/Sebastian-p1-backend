package com.revature.PureDataBase2.services;

import java.util.List;
//import java.util.Optional;

import org.springframework.stereotype.Service;

import com.revature.PureDataBase2.repositories.LibraryTagRepository;
import com.revature.PureDataBase2.entities.LibraryTag;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class LibraryTagService {
    private final LibraryTagRepository libraryTagRepo;

    public List<LibraryTag> getAll() {
        return libraryTagRepo.findAll();
    }
}
