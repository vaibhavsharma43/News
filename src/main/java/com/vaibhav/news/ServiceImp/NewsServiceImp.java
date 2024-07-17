	package com.vaibhav.news.ServiceImp;

	import com.vaibhav.news.Entity.News;
	import com.vaibhav.news.Repo.NewsRepository;
	import com.vaibhav.news.Service.NewsService;
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.stereotype.Service;

	import java.util.List;
	import java.util.Optional;

	@Service
	public class NewsServiceImp implements NewsService {

		@Autowired
		private NewsRepository newsRepository;

		public News saveNews(News news) {
			return newsRepository.save(news);
		}
		public List<News> getAllNews() {
			return newsRepository.findAll();
		}

		@Override
		public boolean deleteNews(String Id) {
			if(newsRepository.existsById(Id)){
				newsRepository.deleteById(Id);
				return true;
			}

			return false;
		}

		private void updateNewsFields(News existingNews, News newNews) {

			if(newNews.getAuthor() != null) {
				existingNews.setAuthor(newNews.getAuthor());
			}
			if(newNews.getTitle() != null) {
				existingNews.setTitle(newNews.getTitle());
			}if(newNews.getDescription() != null) {
				existingNews.setDescription(newNews.getDescription());
			}if(newNews.getUrl() != null) {
				existingNews.setUrl(newNews.getUrl());
			}if(newNews.getUrlToImage() != null) {
				existingNews.setUrlToImage(newNews.getUrlToImage());
			}if(newNews.getPublishedAt() != null) {
				existingNews.setPublishedAt(newNews.getPublishedAt());
			}
			if(newNews.getContent() != null) {
				existingNews.setContent(newNews.getContent());
			}
		}
		@Override
		public boolean updateNews(String Id, News news) {

				Optional<News> existingNews = newsRepository.findById(Id);
				if(existingNews.isPresent()){
					updateNewsFields(existingNews.get(),news);
					newsRepository.save(existingNews.get());
					return true;

				}else{
					return false;
				}



		}


	}
