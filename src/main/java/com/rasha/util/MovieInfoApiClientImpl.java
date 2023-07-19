package com.rasha.util;

import com.rasha.dto.MovieInfoResponse;
import com.rasha.entity.MovieInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MovieInfoApiClientImpl implements MovieInfoApiClient {

    @Value("${omdb.url.path}")
    private final String omdbUrlPath;
    @Value("${omdb.api.key}")
    private final String apikey;
    private final RestTemplate restTemplate;
    private final MovieInfoMapper movieInfoMapper;

    public MovieInfo getMovieFromAPIByTitle(String title) {
        String url = omdbUrlPath + "?apikey={apikey}&t={title}";
        ResponseEntity<MovieInfoResponse> response = restTemplate.getForEntity(url, MovieInfoResponse.class, new HashMap<>() {{
            put("apikey", apikey);
            put("title", title);
        }});
        return handleResponse(Objects.requireNonNull(response.getBody()));
    }

    @Override
    public MovieInfo getMovieFromAPIByImdbID(String imdbID) {
        String url = omdbUrlPath + "?apikey={apikey}&i={imdbID}";
        ResponseEntity<MovieInfoResponse> response = restTemplate.getForEntity(url, MovieInfoResponse.class, new HashMap<>() {{
            put("apikey", apikey);
            put("imdbID", imdbID);
        }});
        return handleResponse(Objects.requireNonNull(response.getBody()));
    }

    private MovieInfo handleResponse(MovieInfoResponse response) {
        if (!response.getResponse())
            throw new IllegalStateException(response.getError()); //@TODO : we can create a custom exception that provides more specific information about the failure
        return movieInfoMapper.toMovieInfo(response);
    }
}
