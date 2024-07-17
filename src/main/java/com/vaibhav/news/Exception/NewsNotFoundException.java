package com.vaibhav.news.Exception;

public class NewsNotFoundException  extends RuntimeException{
    public NewsNotFoundException(String message){
        super(message);
    }
}
