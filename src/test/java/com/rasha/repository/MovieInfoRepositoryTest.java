package com.rasha.repository;

import com.rasha.entity.MovieInfo;
import com.rasha.repository.MovieInfoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.client.RestTemplateAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class MovieInfoRepositoryTest {

    @TestConfiguration
    static class TestConfig {
        @Bean
        public RestTemplateBuilder restTemplateBuilder() {
            return new RestTemplateBuilder();
        }
    }

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private MovieInfoRepository movieInfoRepository;

    @Test
    public void testFindByTitleContainingIgnoreCase() {
        // Create a sample MovieInfo entity
        MovieInfo movieInfo = new MovieInfo();
        movieInfo.setTitle("The Matrix");
        movieInfo.setYear("1999");
        entityManager.persist(movieInfo);
        entityManager.flush();

        // Call the repository method
        Optional<MovieInfo> foundMovieInfo = movieInfoRepository.findFirstByTitleContainingIgnoreCase("matrix");

        // Assertions
        assertTrue(foundMovieInfo.isPresent());
        assertEquals("The Matrix", foundMovieInfo.get().getTitle());
        assertEquals("1999", foundMovieInfo.get().getYear());
    }

    @Test
    public void testFindByImdbIDIgnoreCase() {
        // Create a sample MovieInfo entity
        MovieInfo movieInfo = new MovieInfo();
        movieInfo.setTitle("The Matrix");
        movieInfo.setImdbID("tt0133093");
        entityManager.persist(movieInfo);
        entityManager.flush();

        // Call the repository method
        Optional<MovieInfo> foundMovieInfo = movieInfoRepository.findByImdbIDIgnoreCase("TT0133093");

        // Assertions
        assertTrue(foundMovieInfo.isPresent());
        assertEquals("The Matrix", foundMovieInfo.get().getTitle());
        assertEquals("tt0133093", foundMovieInfo.get().getImdbID());
    }
}
