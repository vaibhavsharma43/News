package com.vaibhav.news.Service;

import com.vaibhav.news.Entity.News;
import org.bson.types.ObjectId;

import java.util.List;

public interface NewsService {

    News saveNews(News news, String userName);
    List<News> getAllNews();
    boolean deleteNews(ObjectId Id, String userName);
    boolean updateNews(ObjectId Id, News news, String userName);
}
