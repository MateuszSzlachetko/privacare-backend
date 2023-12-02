package com.privacare.service;

import com.privacare.model.dto.request.NewsEditRequestDTO;
import com.privacare.model.dto.request.NewsRequestDTO;
import com.privacare.model.dto.response.NewsResponseDTO;
import com.privacare.model.entity.News;
import com.privacare.model.entity.User;
import com.privacare.repository.NewsRepository;
import com.privacare.utilities.exception.custom.not_found.NewsNotFoundException;
import com.privacare.utilities.security.FireAuthToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.*;
import org.springframework.security.core.context.SecurityContextHolder;
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
        FireAuthToken authToken = (FireAuthToken) SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authToken.getUid());
        System.out.println(authToken.isAdmin());

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());

        Page<News> result = this.newsRepository.findAll(pageable);

        List<NewsResponseDTO> mappedNews = result.getContent().stream()
                .map(NewsService::mapNewsToNewsResponse)
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
        return mapNewsToNewsResponse(news);
    }

    public Integer editNews(NewsEditRequestDTO newsEditRequestDTO) {
        this.newsRepository.findById(newsEditRequestDTO.getId()).orElseThrow(
                () -> new NewsNotFoundException(newsEditRequestDTO.getId()));

        return this.newsRepository.updateNewsBy(
                newsEditRequestDTO.getId(),
                newsEditRequestDTO.getTitle(),
                newsEditRequestDTO.getContent());
    }

    public void deleteNews(UUID newsId) throws EmptyResultDataAccessException {
        try {
            this.newsRepository.deleteById(newsId);
        } catch (EmptyResultDataAccessException e) {
            throw new NewsNotFoundException(newsId);
        }
    }

    public NewsResponseDTO getNewsBy(UUID newsId) {
        News news = this.newsRepository.findById(newsId).orElseThrow(() -> new NewsNotFoundException(newsId));
        return mapNewsToNewsResponse(news);
    }

    private static NewsResponseDTO mapNewsToNewsResponse(News news) {
        return NewsResponseDTO.builder()
                .id(news.getId())
                .creatorFullName(news.getCreator().getName() + " " + news.getCreator().getSurname())
                .createdAt(news.getCreatedAt())
                .title(news.getTitle())
                .content(news.getContent())
                .build();
    }
}
