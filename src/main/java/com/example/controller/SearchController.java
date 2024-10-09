package com.example.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.service.SearchService;

@RestController
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private SearchService searchService;

    @GetMapping("/all")
    public ResponseEntity<List<Map<String, Object>>> searchAllCollections(@RequestBody Map<String, String> requestPayload) {
        String searchKey = requestPayload.get("searchKey");
        String searchValue = requestPayload.get("searchValue");
        
        List<Map<String, Object>> results = searchService.searchAllCollections(searchKey, searchValue);
        return ResponseEntity.ok(results);
    }
}
