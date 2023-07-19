package com.rasha.util;

import com.rasha.entity.MovieInfo;

public interface MovieInfoApiClient {

    MovieInfo getMovieFromAPIByTitle(String title);

    MovieInfo getMovieFromAPIByImdbID(String imdbID);
}
