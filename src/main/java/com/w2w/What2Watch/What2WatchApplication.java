package com.w2w.What2Watch;

import com.w2w.What2Watch.repositories.MovieRepository;
import com.w2w.What2Watch.repositories.myHelloWorldCollectionRepository;
import com.w2w.helper.CSVFileExtractor;
import com.w2w.model.Movie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@SpringBootApplication
@RestController
public class What2WatchApplication {

	private static final Logger logger = LoggerFactory.getLogger(What2WatchApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(What2WatchApplication.class, args);
	}

	@Autowired
	private myHelloWorldCollectionRepository myHelloWorldCollectionRepository;

	@Autowired
	private MovieRepository movieRepository;

	@GetMapping("/hello")
	public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
		logger.debug("Inside hello");
		return String.format("Hello World !");
	}

	@GetMapping("/import")
	public String importFile() {
		try {
			List<Movie> movies = CSVFileExtractor.extract("src/main/resources/movie-dataset/tmdb_5000_movies.csv");
			if(movies != null && movies.size() > 0) {
				movieRepository.deleteAll();
				movieRepository.insert(movies);
			}

			return "Imported " + movies.size() + " movies";
		}
		catch (Exception ex){
			return "Exception occured: " + ex.getMessage();
		}
	}
}
