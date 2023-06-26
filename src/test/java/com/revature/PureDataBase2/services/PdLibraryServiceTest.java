package com.revature.PureDataBase2.services;

import com.revature.PureDataBase2.DTO.requests.PdEditLibrary;
import com.revature.PureDataBase2.DTO.requests.PdEditObject;
import com.revature.PureDataBase2.entities.*;
import com.revature.PureDataBase2.repositories.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

@ExtendWith(MockitoExtension.class)
class PdLibraryServiceTest {
    private PdLibraryService libraryService;

    @Mock
    private PdLibraryRepository libraryRepo;

    @Mock
    private TagService tagService;

    @Mock
    private LTagService lTagService;

    @Mock
    private PdObjectRepository objectRepo;

    @BeforeEach
    public void setup() {
        libraryService = new PdLibraryService(libraryRepo, objectRepo, tagService, lTagService);
    }

    @Test
    public void updateObjectTest() {
        PdLibraryService secretService = spy(libraryService);
        PdLibrary library = new PdLibrary();
        library.setName("myLib");
        PdObject prevObj = new PdObject();
        prevObj.setName("myObj");
        PdEditObject input = new PdEditObject();
        input.setName("myObj");
        input.setLibName("myLib");
        prevObj.setLibrary(library);
        User user = new User("bobb", "ham", new Role("USER"));
        library.setName("myLib");
        Set<String> inputTags = new HashSet<String>();
        inputTags.add("juice");
        inputTags.add("hamburger");
        inputTags.add("fries");
        inputTags.add("soda");
        input.setObjectTags(inputTags);
        String[] prevTagNames = {"hot-dog", "juice", "soda"};
        Set<ObjectTag> objectTags = new HashSet<ObjectTag>();
        for(String prevTagName : prevTagNames) {
            Tag thisTag = new Tag(prevTagName);
            objectTags.add(new ObjectTag(prevObj, thisTag));
        }
        prevObj.setObjectTags(objectTags);
        Set<String> remainderInput = new HashSet<String>();
        remainderInput.add("hamburger");
        remainderInput.add("fries");
        List<Tag> remainderTags = new ArrayList<Tag>();
        remainderTags.add(new Tag("hamburger"));
        remainderTags.add(new Tag("fries"));
        when(tagService.getByNames(remainderInput)).thenReturn(remainderTags);
        doReturn(library).when(secretService).getByName("myLib");
        secretService.updateObject(input, prevObj, user);
        verify(objectRepo, times(1)).save(prevObj);
        Set<ObjectTag> outputTags = prevObj.getObjectTags();
        assertTrue(outputTags.size() == 4);
        Set<String> outputStrings = new HashSet<String>();
        for(ObjectTag outputTag : outputTags) {
            outputStrings.add(outputTag.getTag().getName());
        }
        assertTrue(outputStrings.contains("hamburger"));
        assertTrue(outputStrings.contains("soda"));
        assertTrue(outputStrings.contains("fries"));
        assertTrue(outputStrings.contains("juice"));
    }

    @Test
    public void updateLibraryTest() {
        PdLibrary library = new PdLibrary();
        PdEditLibrary input = new PdEditLibrary();
        User user = new User("bobb", "ham", new Role("USER"));
        input.setName("myLib");
        Set<String> inputTags = new HashSet<String>();
        inputTags.add("hamburger");
        inputTags.add("soda");
        inputTags.add("fries");
        input.setLibraryTags(inputTags);
        String[] prevTagNames = {"juice", "soda", "hot-dog"};
        Set<LibraryTag> libraryTags = new HashSet<LibraryTag>();
        for(String prevTagName : prevTagNames) {
            LTag thisTag = new LTag(prevTagName);
            libraryTags.add(new LibraryTag(library, thisTag));
        }
        library.setLibraryTags(libraryTags);
        library.setName("myLib");
        when(libraryRepo.findByName("myLib")).thenReturn(Optional.empty());
        Set<String> remainderInput = new HashSet<String>();
        remainderInput.add("hamburger");
        remainderInput.add("fries");
        List<LTag> remainderTags = new ArrayList<LTag>();
        remainderTags.add(new LTag("hamburger"));
        remainderTags.add(new LTag("fries"));
        when(lTagService.getByNames(remainderInput)).thenReturn(remainderTags);
        libraryService.updateLibrary(input, library, user);
        verify(libraryRepo, times(1)).save(library);
        Set<LibraryTag> outputTags = library.getLibraryTags();
        assertTrue(outputTags.size() == 3);
        Set<String> outputStrings = new HashSet<String>();
        for(LibraryTag outputTag : outputTags) {
            outputStrings.add(outputTag.getTag().getName());
        }
        assertTrue(outputStrings.contains("hamburger"));
        assertTrue(outputStrings.contains("soda"));
        assertTrue(outputStrings.contains("fries"));
    }
}
