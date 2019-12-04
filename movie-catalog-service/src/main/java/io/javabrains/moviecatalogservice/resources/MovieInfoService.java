package io.javabrains.moviecatalogservice.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import io.javabrains.moviecatalogservice.models.CatalogItem;
import io.javabrains.moviecatalogservice.models.Movie;
import io.javabrains.moviecatalogservice.models.Rating;
@Service
public class MovieInfoService {
	
	@Autowired
	private RestTemplate restTemplate;
	@HystrixCommand(fallbackMethod = "getCatalogItemFallBack")
	public CatalogItem getCatalogItem(Rating rating) {
		Movie movie = restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMovieId(), Movie.class);
		return new CatalogItem(movie.getName(), movie.getDescription(), rating.getRating());
	}
	
	public CatalogItem getCatalogItemFallBack(Rating rating) {
		return new CatalogItem("NO Movie", "no desc", 0);
	}

}
