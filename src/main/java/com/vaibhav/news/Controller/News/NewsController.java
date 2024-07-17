package com.vaibhav.news.Controller.News;

import com.vaibhav.news.Entity.News;
import com.vaibhav.news.Service.NewsService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/news")
public class NewsController {

    @Autowired
    private NewsService newsService;


    @PostMapping()
    public News createNews(@RequestBody News news ) {

        try {
            Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();

            return newsService.saveNews(news,userName);
        }catch (Exception e){
            throw e;
        }

    }





    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteNews(@PathVariable ObjectId id) {
        try {
            Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            boolean isDeleted = newsService.deleteNews(id,userName);
            if (isDeleted) {
                return ResponseEntity.ok("News deleted successfully");
            } else {
                return ResponseEntity.ok("News not deleted successfully");
            }
        }catch (Exception e){
            throw e;
        }

    }


    @PutMapping("/{id}")
    public ResponseEntity<String> updateNews(@PathVariable ObjectId id, @RequestBody News
            news) {
        try {

            Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            boolean isUpdated = newsService.updateNews(id, news,userName);
            if (isUpdated) {
                return ResponseEntity.ok("News updated successfully");
            } else {
                return ResponseEntity.ok("News not updated successfully");
            }
        }catch (Exception e){
            throw e;
        }
    }
}