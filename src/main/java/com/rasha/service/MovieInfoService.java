package com.rasha.service;

import com.rasha.dto.GetMovieInfoRequest;
import com.rasha.entity.MovieInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public interface MovieInfoService {

    @GetMapping
    ResponseEntity<MovieInfo> getMovieInfo(GetMovieInfoRequest request);

}
