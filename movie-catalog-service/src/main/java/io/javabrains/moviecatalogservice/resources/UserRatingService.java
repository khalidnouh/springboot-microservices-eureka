package io.javabrains.moviecatalogservice.resources;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import io.javabrains.moviecatalogservice.models.Rating;
import io.javabrains.moviecatalogservice.models.UserRating;
@Service
public class UserRatingService {
	
	@Autowired
	private RestTemplate restTemplate;
	@HystrixCommand(fallbackMethod = "getUserRatingsFallBack")
	public UserRating getUserRatings(@PathVariable("userId") String userId) {
		return restTemplate.getForObject("http://ratings-data-service/ratingsdata/user/" + userId, UserRating.class);
	}

	public UserRating getUserRatingsFallBack(@PathVariable("userId") String userId) {
		UserRating userRating=new UserRating();
		userRating.setUserId(userId);
		userRating.setRatings(Arrays.asList(new Rating("0", 0)));
		return userRating;
	}

}
