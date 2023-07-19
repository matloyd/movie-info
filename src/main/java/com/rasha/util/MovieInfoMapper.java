package com.rasha.util;

import com.rasha.dto.MovieInfoResponse;
import com.rasha.dto.RatingResponse;
import com.rasha.entity.MovieInfo;
import com.rasha.entity.Rating;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MovieInfoMapper {

    @Mapping(target = "id", ignore = true)
    MovieInfo toMovieInfo(MovieInfoResponse movieInfoResponse);

    @Mapping(target = "id", ignore = true)
    Rating toRating(RatingResponse ratingResponse);
}