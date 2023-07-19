package com.rasha.util;

import com.rasha.dto.MovieInfoResponse;
import com.rasha.entity.MovieInfo;
import com.rasha.util.MovieInfoApiClient;
import com.rasha.util.MovieInfoApiClientImpl;
import com.rasha.util.MovieInfoMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MovieInfoApiClientImplTest {

    private static final String OMDB_URL_PATH = "http://www.omdbapi.com/";
    private static final String API_KEY = "testkey";

    @Mock
    private RestTemplate restTemplate;
    @Mock
    private MovieInfoMapper movieInfoMapper;

    private MovieInfoApiClient movieInfoApiClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        movieInfoApiClient = new MovieInfoApiClientImpl(OMDB_URL_PATH, API_KEY, restTemplate, movieInfoMapper);
    }

    @Test
    void getMovieFromAPIByTitle_ValidTitle_ReturnsMovieInfo() {
        // Arrange
        String title = "Test Movie";
        String url = OMDB_URL_PATH + "?apikey={apikey}" + "&t={title}";

        MovieInfoResponse movieInfoResponse = new MovieInfoResponse();
        movieInfoResponse.setResponse(true);
        movieInfoResponse.setTitle(title);

        MovieInfo expectedMovieInfo = new MovieInfo();
        expectedMovieInfo.setTitle(title);

        Map<String, String> urlParams = new HashMap<>();
        urlParams.put("title", title);
        urlParams.put("apikey", API_KEY);

        when(restTemplate.getForEntity(eq(url), eq(MovieInfoResponse.class), eq(urlParams)))
                .thenReturn(ResponseEntity.ok(movieInfoResponse));

        when(movieInfoMapper.toMovieInfo(eq(movieInfoResponse))).thenReturn(expectedMovieInfo);

        // Act
        MovieInfo result = movieInfoApiClient.getMovieFromAPIByTitle(title);

        // Assert
        assertNotNull(result);
        assertEquals(expectedMovieInfo.getTitle(), result.getTitle());
        verify(restTemplate, times(1)).getForEntity(eq(url), eq(MovieInfoResponse.class), eq(urlParams));
        verify(movieInfoMapper, times(1)).toMovieInfo(eq(movieInfoResponse));
    }

    @Test
    void getMovieFromAPIByImdbID_ValidImdbID_ReturnsMovieInfo() {
        // Arrange
        String imdbID = "tt1234567";
        String url = OMDB_URL_PATH + "?apikey={apikey}" + "&i={imdbID}";

        MovieInfoResponse movieInfoResponse = new MovieInfoResponse();
        movieInfoResponse.setResponse(true);
        movieInfoResponse.setTitle("Test Movie");

        MovieInfo expectedMovieInfo = new MovieInfo();
        expectedMovieInfo.setTitle("Test Movie");

        Map<String, String> urlParams = new HashMap<>();
        urlParams.put("apikey", API_KEY);
        urlParams.put("imdbID", imdbID);

        when(restTemplate.getForEntity(eq(url), eq(MovieInfoResponse.class), eq(urlParams)))
                .thenReturn(ResponseEntity.ok(movieInfoResponse));

        when(movieInfoMapper.toMovieInfo(eq(movieInfoResponse))).thenReturn(expectedMovieInfo);

        // Act
        MovieInfo result = movieInfoApiClient.getMovieFromAPIByImdbID(imdbID);

        // Assert
        assertNotNull(result);
        assertEquals(expectedMovieInfo.getTitle(), result.getTitle());
        verify(restTemplate, times(1)).getForEntity(eq(url), eq(MovieInfoResponse.class), eq(urlParams));
        verify(movieInfoMapper, times(1)).toMovieInfo(eq(movieInfoResponse));
    }

    @Test
    void getMovieFromAPIByTitle_InvalidResponse_ThrowsIllegalStateException() {
        // Arrange
        String title = "Test Movie";
        String url = OMDB_URL_PATH + "?apikey={apikey}" + "&t={title}";

        MovieInfoResponse movieInfoResponse = new MovieInfoResponse();
        movieInfoResponse.setResponse(false);
        movieInfoResponse.setError("Invalid response");

        Map<String, String> urlParams = new HashMap<>();
        urlParams.put("title", title);
        urlParams.put("apikey", API_KEY);

        when(restTemplate.getForEntity(eq(url), eq(MovieInfoResponse.class), eq(urlParams)))
                .thenReturn(ResponseEntity.ok(movieInfoResponse));

        // Act & Assert
        assertThrows(IllegalStateException.class, () -> movieInfoApiClient.getMovieFromAPIByTitle(title));
        verify(restTemplate, times(1)).getForEntity(eq(url), eq(MovieInfoResponse.class), eq(urlParams));
        verify(movieInfoMapper, never()).toMovieInfo(any(MovieInfoResponse.class));
    }
}
