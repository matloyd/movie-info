package com.rasha.repository;

import com.rasha.entity.MovieInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MovieInfoRepository extends JpaRepository<MovieInfo, Long> {

    Optional<MovieInfo> findFirstByTitleContainingIgnoreCase(String title);

    Optional<MovieInfo> findByImdbIDIgnoreCase(String title);
}
