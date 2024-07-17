package com.vaibhav.news.Service;

import com.vaibhav.news.Entity.News;

import java.util.List;

public interface NewsService {
    News saveNews(News news);
    List<News> getAllNews();
    boolean deleteNews(String Id);
    boolean updateNews(String Id,News news);
}
