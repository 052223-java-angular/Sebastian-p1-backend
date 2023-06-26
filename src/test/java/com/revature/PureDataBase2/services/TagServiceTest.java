package com.revature.PureDataBase2.services;

import com.revature.PureDataBase2.repositories.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
class TagServiceTest {
    private TagService tagService;

    @Mock
    private TagRepository tagRepo;

    @Mock
    private PdObjectRepository objectRepo;

    @BeforeEach
    public void setup() {
        tagService = new TagService(tagRepo, objectRepo);
    }

    @Test
    public void isValidTagTest() {
        assertTrue(tagService.isValidTag("blueberry"));
        assertFalse(tagService.isValidTag(" blueberry"));
        assertTrue(tagService.isValidTag("blue-berry"));
        assertFalse(tagService.isValidTag("b1ue83rry"));
    }
}
