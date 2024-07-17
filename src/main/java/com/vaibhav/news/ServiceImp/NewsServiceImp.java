package com.vaibhav.news.ServiceImp;

import com.vaibhav.news.Entity.News;
import com.vaibhav.news.Entity.User;
import com.vaibhav.news.Repo.NewsRepository;
import com.vaibhav.news.Repo.UserRepository;
import com.vaibhav.news.Service.NewsService;
import com.vaibhav.news.Service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class NewsServiceImp implements NewsService {

    @Autowired
    private NewsRepository newsRepository;
    @Autowired
    private UserService userService;
	@Autowired
	private UserRepository userRepository;
    @Override
    @Transactional
    public News saveNews(News NewNews, String userName) {
        try {

            User user = userRepository.findByUserName(userName);
            newsRepository.save(NewNews);
            user.getNews().add(NewNews);
            userService.saveExistUser(user);

            return NewNews;
        } catch (Exception e) {

            throw new RuntimeException("Error in saving new news with userName: " + userName, e);
        }
    }

    @Override
    public List<News> getAllNews() {
        try {

            return newsRepository.findAll();
        } catch (Exception e)
        {

        throw new RuntimeException("Error occurred while getting all news", e);
        }
    }

    @Override
    public boolean deleteNews(ObjectId Id, String userName) {
        try {
            User user = userService.findByUsername(userName);
            List<News> newsList = user.getNews();
            boolean deleted = newsList.removeIf(x -> x.getId().equals(Id));
            if (deleted) {
                userService.saveExistUser(user);
                newsRepository.deleteById(Id);

                return true;
            } else {
                return false;
            }


        } catch (Exception e) {
            throw new RuntimeException("Error in deleting news with userName" + userName);
        }

    }

    private void updateNewsFields(News existingNews, News newNews) {

        if (newNews.getAuthor() != null) {
            existingNews.setAuthor(newNews.getAuthor());
        }
        if (newNews.getTitle() != null) {
            existingNews.setTitle(newNews.getTitle());
        }
        if (newNews.getDescription() != null) {
            existingNews.setDescription(newNews.getDescription());
        }
        if (newNews.getUrl() != null) {
            existingNews.setUrl(newNews.getUrl());
        }
        if (newNews.getUrlToImage() != null) {
            existingNews.setUrlToImage(newNews.getUrlToImage());
        }
        if (newNews.getPublishedAt() != null) {
            existingNews.setPublishedAt(newNews.getPublishedAt());
        }
        if (newNews.getContent() != null) {
            existingNews.setContent(newNews.getContent());
        }
    }

    @Override
    public boolean updateNews(ObjectId Id, News news, String userName) {
        try {
            User user = userService.findByUsername(userName);
            if (user != null) {
                List<News> newsList = user.getNews();
                boolean getsFind = newsList.stream().anyMatch(x -> x.getId().equals(Id));
                if (getsFind) {
                    Optional<News> existingNews = newsRepository.findById(Id);
                    if (existingNews.isPresent()) {
                        updateNewsFields(existingNews.get(), news);
                        newsRepository.save(existingNews.get());
                        return true;

                    } else {
                        return false;
                    }
                } else {
                    return false;
                }

            } else {
                return false;
            }

        } catch (Exception e) {
            throw new RuntimeException("Error in updating news with userName" + userName);
        }


    }


}
