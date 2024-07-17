package com.vaibhav.news.Controller;

import com.vaibhav.news.Entity.News;


import com.vaibhav.news.Service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/news")
public class NewsController {

    @Autowired
   private NewsService newsService;
    @PostMapping
    public News createNews(@RequestBody News news) {
        return newsService.saveNews(news);
    }

    @GetMapping
    public List<News> getAllNews() {
        return newsService.getAllNews();

    }
    @DeleteMapping("/{Id}")
    public ResponseEntity<String> deleteNews(@PathVariable String Id) {

        boolean isDeleted=  newsService.deleteNews(Id);
        if(isDeleted){
            return ResponseEntity.ok("News deleted successfully");
        }else{
            return ResponseEntity.ok("News not deleted successfully");
        }

    }
    @PutMapping("/{Id}")
    public ResponseEntity<String> updateNews(@PathVariable String Id, @RequestBody News news) {
        boolean isUpdated=  newsService.updateNews(Id, news);
        if (isUpdated) {
            return ResponseEntity.ok("News updated successfully");
        }else{
            return ResponseEntity.ok("News not updated successfully");
        }
    }
}
