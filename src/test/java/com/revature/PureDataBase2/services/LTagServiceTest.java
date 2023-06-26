package com.revature.PureDataBase2.services;

import com.revature.PureDataBase2.repositories.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
class LTagServiceTest {
    private LTagService lTagService;

    @Mock
    private LTagRepository lTagRepo;

    @Mock
    private PdLibraryRepository libraryRepo;

    @BeforeEach
    public void setup() {
        lTagService = new LTagService(lTagRepo, libraryRepo);
    }

    @Test
    public void isValidTagTest() {
        assertTrue(lTagService.isValidLTag("blueberry"));
        assertFalse(lTagService.isValidLTag(" blueberry"));
        assertTrue(lTagService.isValidLTag("blue-berry"));
        assertFalse(lTagService.isValidLTag("b1ue83rry"));
    }
}
