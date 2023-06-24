package com.revature.PureDataBase2.controllers;

import java.util.List;
import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.revature.PureDataBase2.util.custom_exceptions.TagNotFoundException;

import com.revature.PureDataBase2.entities.PdObject;
import com.revature.PureDataBase2.entities.PdLibrary;
import com.revature.PureDataBase2.services.TagService;
import com.revature.PureDataBase2.services.LTagService;
import com.revature.PureDataBase2.services.PdLibraryService;
import com.revature.PureDataBase2.DTO.responses.SearchResults;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@CrossOrigin
@RequestMapping("/search")
public class SearchController {
    // dependency injection ie. services
    private final PdLibraryService libraryService;
    private final TagService tagService;
    private final LTagService lTagService;

    @GetMapping("/{term}")
    public ResponseEntity<SearchResults> getResults(@PathVariable String term,
        @RequestParam(required = false) List<String> methods) {
        SearchResults searchResults = new SearchResults();
        if(methods == null) methods = new ArrayList<String>();
        if(methods.size() == 0) methods.add("all");
        boolean all = methods.contains("all");
        if(all || methods.contains("object_tag")) {
            try{
                List<PdObject> tagObjs = tagService.getObjectsByTagName(term);
                List<String> resStrings = new ArrayList<String>();
                for(PdObject tagObj : tagObjs) {
                    resStrings.add(tagObj.getLibrary().getName() + '/' + tagObj.getName());
                }
                searchResults.setObjTagResults(resStrings);
            } catch(TagNotFoundException e) {
                searchResults.setObjTagResults(new ArrayList<String>(0));
            }
        }
        if(all || methods.contains("library_tag")) {
            try{
                List<PdLibrary> tagLibs = lTagService.getLibrariesByTagName(term);
                List<String> resStrings = new ArrayList<String>();
                for(PdLibrary tagLib : tagLibs) {
                    resStrings.add(tagLib.getName());
                }
                searchResults.setLibTagResults(resStrings);
            } catch(TagNotFoundException e) {
                searchResults.setLibTagResults(new ArrayList<String>(0));
            }
        }
        if(all || methods.contains("library")) {
            List<PdLibrary> libs = libraryService.getByNameLike(term);
            List<String> resStrings = new ArrayList<String>();
            for(PdLibrary lib : libs) {
                resStrings.add(lib.getName());
            }
            searchResults.setLibraryResults(resStrings);
        }
        if(all || methods.contains("object")) {
            List<PdObject> objs = libraryService.getObjectByNameLike(term);
            List<String> resStrings = new ArrayList<String>();
            for(PdObject obj : objs) {
                resStrings.add(obj.getLibrary().getName() + '/' + obj.getName());
            }
            searchResults.setObjectResults(resStrings);
        }
        if(all || methods.contains("author")) {
            List<PdLibrary> libs = libraryService.getAuthorLibsByNameLike(term);
            List<String> resStrings = new ArrayList<String>();
            for(PdLibrary lib : libs) {
                resStrings.add(lib.getName());
            }
            searchResults.setAuthorResults(resStrings);
        }
        return ResponseEntity.status(HttpStatus.OK).body(searchResults);
    }
}
