package com.rasha.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GetMovieInfoRequest {

    private String imdbID;
    private String title;

}
