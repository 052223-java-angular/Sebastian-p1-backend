package com.revature.PureDataBase2.controllers;

import java.util.List;
import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.revature.PureDataBase2.util.custom_exceptions.TagNotFoundException;

import com.revature.PureDataBase2.entities.PdObject;
import com.revature.PureDataBase2.services.TagService;
import com.revature.PureDataBase2.services.PdLibraryService;
import com.revature.PureDataBase2.DTO.responses.SearchResults;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/search")
public class SearchController {
    // dependency injection ie. services
    private final PdLibraryService libraryService;
    private final TagService tagService;

    @GetMapping("/{term}")
    public ResponseEntity<SearchResults> getResults(@PathVariable String term,
        @RequestParam(required = false) List<String> methods) {
        SearchResults searchResults = new SearchResults();
        if(methods == null) methods = new ArrayList<String>();
        if(methods.size() == 0) methods.add("all");
        boolean all = methods.contains("all");
        if(all || methods.contains("tag")) {
            try{
                List<PdObject> tagObjs = tagService.getObjectsByTagName(term);
                searchResults.setTagResults(tagObjs);
            } catch(TagNotFoundException e) {
                searchResults.setTagResults(new ArrayList<PdObject>(0));
            }
        }
        if(all || methods.contains("library")) {
            searchResults.setLibraryResults(libraryService.getByNameLike(term));
        }
        if(all || methods.contains("object")) {
            searchResults.setObjectResults(libraryService.getObjectByNameLike(term));
        }
        if(all || methods.contains("author")) {
            searchResults.setAuthorResults(libraryService.getAuthorLibsByNameLike(term));
        }
        return ResponseEntity.status(HttpStatus.OK).body(searchResults);
    }
}
