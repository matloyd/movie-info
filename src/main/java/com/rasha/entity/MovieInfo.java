package com.rasha.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "movie_infos")
public class MovieInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "`year`")
    private String year;

    @Column(name = "rated")
    private String rated;

    @Column(name = "released")
    private String released;

    @Column(name = "runtime")
    private String runtime;

    @Column(name = "genre")
    private String genre;

    @Column(name = "director")
    private String director;

    @Column(name = "writer")
    private String writer;

    @Column(name = "actors")
    private String actors;

    @Column(name = "plot", length = 1000)
    private String plot;

    @Column(name = "language")
    private String language;

    @Column(name = "country")
    private String country;

    @Column(name = "awards")
    private String awards;

    @Column(name = "poster")
    private String poster;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "movie_info_id")
    private List<Rating> ratings;

    @Column(name = "metascore")
    private String metascore;

    @Column(name = "imdb_rating")
    private String imdbRating;

    @Column(name = "imdb_votes")
    private String imdbVotes;

    @Column(name = "imdb_id", unique = true)
    private String imdbID;

    @Column(name = "type")
    private String type;

    @Column(name = "dvd")
    private String dvd;

    @Column(name = "box_office")
    private String boxOffice;

    @Column(name = "production")
    private String production;

    @Column(name = "website")
    private String website;

    @Column(name = "is_best_picture_winner")
    private Boolean isBestPictureWinner;

    @Column(name = "total_seasons")
    private String totalSeasons;

}
