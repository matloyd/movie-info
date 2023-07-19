package com.rasha.service;

import com.rasha.dto.GetMovieInfoRequest;
import com.rasha.entity.MovieInfo;
import com.rasha.repository.MovieInfoRepository;
import com.rasha.util.BestPictureWinnerService;
import com.rasha.util.MovieInfoApiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MovieInfoServiceImpl implements MovieInfoService {

    private final MovieInfoRepository movieInfoRepository;
    private final MovieInfoApiClient movieInfoApiClient;
    private final BestPictureWinnerService bestPictureWinnerService;

    @Override
    public ResponseEntity<MovieInfo> getMovieInfo(GetMovieInfoRequest request) {

        if (request.getTitle() == null && request.getImdbID() == null)
            throw new IllegalArgumentException("Both \"title\" and \"imdbID\" are optional at least one argument is required.");

        MovieInfo result;

        if (request.getTitle() != null)
            result = movieInfoRepository.findFirstByTitleContainingIgnoreCase(request.getTitle()).orElseGet(() ->
                    setBestPictureWinnerAndPersist(movieInfoApiClient.getMovieFromAPIByTitle(request.getTitle())));

        else result = movieInfoRepository.findByImdbIDIgnoreCase(request.getImdbID()).orElseGet(() ->
                setBestPictureWinnerAndPersist(movieInfoApiClient.getMovieFromAPIByImdbID(request.getImdbID())));

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    private MovieInfo setBestPictureWinnerAndPersist(MovieInfo movieInfo) {
        movieInfo.setIsBestPictureWinner(bestPictureWinnerService.isBestPictureWinner(movieInfo.getYear(), movieInfo.getTitle()));
        return movieInfoRepository.save(movieInfo);
    }
}
