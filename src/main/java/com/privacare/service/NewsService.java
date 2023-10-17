package com.privacare.service;

import com.privacare.model.dto.response.NewsResponseDTO;
import com.privacare.model.entity.News;
import com.privacare.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class NewsService {

    private final NewsRepository newsRepository;

    public List<NewsResponseDTO> getNews(Integer page,Integer size) {
        Pageable pageable = PageRequest.of(page,size, Sort.by("createdAt").descending());

        Page<News> result = this.newsRepository.findAll(pageable);

        return result.stream().map(news -> NewsResponseDTO.builder()
                .id(news.getId())
                .creatorFullName(news.getCreator().getName() + " " + news.getCreator().getSurname())
                .createdAt(news.getCreatedAt())
                .title(news.getTitle())
                .content(news.getContent())
                .build())
                .collect(Collectors.toList());
    }
}
