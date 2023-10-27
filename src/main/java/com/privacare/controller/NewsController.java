package com.privacare.controller;

import com.privacare.utilities.exception.ErrorDetails;
import com.privacare.model.dto.request.NewsEditRequestDTO;
import com.privacare.model.dto.request.NewsRequestDTO;
import com.privacare.model.dto.response.NewsResponseDTO;
import com.privacare.service.NewsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
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
        Page<NewsResponseDTO> result = this.newsService.getNews(page, size);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/{newsId}")
    public ResponseEntity<NewsResponseDTO> getNews(@PathVariable UUID newsId) {
        NewsResponseDTO result = this.newsService.getNewsBy(newsId);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<NewsResponseDTO> addNews(@RequestBody @Valid NewsRequestDTO newsRequestDTO) {
        NewsResponseDTO result = this.newsService.addNews(newsRequestDTO);

        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Integer> editNews(@RequestBody NewsEditRequestDTO newsEditRequestDTO) {
        Integer rowsAffected = this.newsService.editNews(newsEditRequestDTO);

        return new ResponseEntity<>(rowsAffected, HttpStatus.OK);
    }

    @DeleteMapping("/{newsId}")
    public ResponseEntity<Object> deleteNews(@PathVariable UUID newsId) {
        try {
            this.newsService.deleteNews(newsId);
        } catch (EmptyResultDataAccessException e) {
            ErrorDetails errorDetails = ErrorDetails.builder()
                    .messages(List.of("News with id: " + newsId + " not found"))
                    .timestamp(LocalDateTime.now())
                    .build();
            return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}