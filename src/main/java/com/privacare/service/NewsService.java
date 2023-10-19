package com.privacare.service;

import com.privacare.model.dto.request.NewsRequestDTO;
import com.privacare.model.dto.response.NewsResponseDTO;
import com.privacare.model.entity.News;
import com.privacare.model.entity.User;
import com.privacare.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class NewsService {

    private final UserService userService;

    private final NewsRepository newsRepository;

    public Page<NewsResponseDTO> getNews(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());

        Page<News> result = this.newsRepository.findAll(pageable);

        List<NewsResponseDTO> mappedNews = result.getContent().stream()
                .map(news -> NewsResponseDTO.builder()
                        .id(news.getId())
                        .creatorFullName(news.getCreator().getName() + " " + news.getCreator().getSurname())
                        .createdAt(news.getCreatedAt())
                        .title(news.getTitle())
                        .content(news.getContent())
                        .build())
                .collect(Collectors.toList());

        return new PageImpl<>(mappedNews, result.getPageable(), result.getTotalElements());
    }

    public NewsResponseDTO addNews(NewsRequestDTO newsRequestDTO) {
        User creator = this.userService.getUserBy(newsRequestDTO.getCreatorId());

        News news = News.builder()
                .creator(creator)
                .createdAt(LocalDateTime.now())
                .title(newsRequestDTO.getTitle())
                .content(newsRequestDTO.getContent())
                .build();

        this.newsRepository.save(news);

        return NewsResponseDTO.builder()
                .id(news.getId())
                .creatorFullName(news.getCreator().getName() + " " + news.getCreator().getSurname())
                .createdAt(news.getCreatedAt())
                .title(news.getTitle())
                .content(news.getContent())
                .build();
    }

    public Integer editNews(UUID newsId, String title, String content) {
        if (title == null && content == null)
            return 0;

        return this.newsRepository.updateNewsBy(newsId, title, content);
    }

    public void deleteNews(UUID newsId) throws EmptyResultDataAccessException {
        this.newsRepository.deleteById(newsId);
    }
}