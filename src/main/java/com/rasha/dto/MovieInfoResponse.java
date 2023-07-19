package com.rasha.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.UpperCamelCaseStrategy.class)
public class MovieInfoResponse {

    private String title;
    private String year;
    private String rated;
    private String released;
    private String runtime;
    private String genre;
    private String director;
    private String writer;
    private String actors;
    private String plot;
    private String language;
    private String country;
    private String awards;
    private String poster;
    private List<RatingResponse> ratings;
    private String metascore;
    private String type;
    private String boxOffice;
    private String production;
    private String website;
    private String source;
    private Boolean response;
    private String error;
    private Boolean isBestPictureWinner;

    @JsonProperty("imdbRating")
    private String imdbRating;

    @JsonProperty("imdbVotes")
    private String imdbVotes;

    @JsonProperty("imdbID")
    private String imdbID;

    @JsonProperty("DVD")
    private String dvd;

    @JsonProperty("totalSeasons")
    private String totalSeasons;

}
