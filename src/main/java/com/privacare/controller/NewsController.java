package com.privacare.controller;

import com.privacare.model.dto.request.NewsEditRequestDTO;
import com.privacare.model.dto.request.NewsRequestDTO;
import com.privacare.model.dto.response.NewsResponseDTO;
import com.privacare.service.NewsService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;


@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/news")
@SecurityRequirement(name = "bearerAuth")
public class NewsController {

    private final NewsService newsService;

    @GetMapping
    public ResponseEntity<Page<NewsResponseDTO>> getNews(
            @RequestParam Integer page,
            @RequestParam Integer size) {
        Page<NewsResponseDTO> result = this.newsService.getNews(page, size);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/{newsId}")
    public ResponseEntity<NewsResponseDTO> getNews(@PathVariable UUID newsId) {
        NewsResponseDTO result = this.newsService.getNewsBy(newsId);
        return ResponseEntity.ok().body(result);
    }

    @PostMapping
    public ResponseEntity<NewsResponseDTO> addNews(@RequestBody @Valid NewsRequestDTO newsRequestDTO) {
        NewsResponseDTO result = this.newsService.addNews(newsRequestDTO);
        return ResponseEntity.ok().body(result);
    }

    @PutMapping
    public ResponseEntity<Integer> editNews(@RequestBody NewsEditRequestDTO newsEditRequestDTO) {
        Integer rowsAffected = this.newsService.editNews(newsEditRequestDTO);
        return ResponseEntity.ok().body(rowsAffected);
    }

    @DeleteMapping("/{newsId}")
    public ResponseEntity<Object> deleteNews(@PathVariable UUID newsId) {
        this.newsService.deleteNews(newsId);
        return ResponseEntity.noContent().build();
    }
}