package com.privacare.controller;

import com.privacare.model.dto.request.NewsRequestDTO;
import com.privacare.model.dto.response.NewsResponseDTO;
import com.privacare.service.NewsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/news")
public class NewsController {

    private final NewsService newsService;

    @GetMapping
    public ResponseEntity<Page<NewsResponseDTO>> getNews(
            @RequestParam Integer page,
            @RequestParam Integer size) {
        Page<NewsResponseDTO> result = this.newsService.getNews(page,size);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<NewsResponseDTO> addNews(@RequestBody @Valid NewsRequestDTO newsRequestDTO){
        NewsResponseDTO result = this.newsService.addNews(newsRequestDTO);

        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PutMapping("/{newsId}")
    public ResponseEntity<Integer> editNews(
            @PathVariable UUID newsId,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String content){
        Integer rowsAffected = this.newsService.editNews(newsId,title,content);

        return new ResponseEntity<>(rowsAffected, HttpStatus.OK);
    }
}