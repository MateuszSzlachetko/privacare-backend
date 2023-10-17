package com.privacare.controller;

import com.privacare.model.dto.response.NewsResponseDTO;
import com.privacare.service.NewsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/news")
public class NewsController {

    private final NewsService newsService;

    @GetMapping
    public ResponseEntity<List<NewsResponseDTO>> getNews(
            @RequestParam Integer page,
            @RequestParam Integer size) {
        List<NewsResponseDTO> result = this.newsService.getNews(page,size);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}