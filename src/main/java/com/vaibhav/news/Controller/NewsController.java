package com.vaibhav.news.Controller;

import com.vaibhav.news.Entity.News;
import com.vaibhav.news.Service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/news")
public class NewsController {

    @Autowired
    private NewsService newsService;

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PostMapping
    public News createNews(@RequestBody News news) {
        return newsService.saveNews(news);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<News> getAllNews() {
        return newsService.getAllNews();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteNews(@PathVariable String id) {
        boolean isDeleted = newsService.deleteNews(id);
        if (isDeleted) {
            return ResponseEntity.ok("News deleted successfully");
        } else {
            return ResponseEntity.ok("News not deleted successfully");
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<String> updateNews(@PathVariable String id, @RequestBody News
            news) {
        boolean isUpdated = newsService.updateNews(id, news);
        if (isUpdated) {
            return ResponseEntity.ok("News updated successfully");
        } else {
            return ResponseEntity.ok("News not updated successfully");
        }
    }
}