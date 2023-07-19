package com.rasha.service;

import com.rasha.dto.GetMovieInfoRequest;
import com.rasha.entity.MovieInfo;
import com.rasha.repository.MovieInfoRepository;
import com.rasha.util.BestPictureWinnerService;
import com.rasha.util.MovieInfoApiClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class MovieInfoServiceTest {

    @Mock
    private MovieInfoRepository movieInfoRepository;

    @Mock
    private MovieInfoApiClient movieInfoApiClient;

    @Mock
    private BestPictureWinnerService bestPictureWinnerService;

    private MovieInfoServiceImpl movieInfoService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        movieInfoService = new MovieInfoServiceImpl(movieInfoRepository, movieInfoApiClient, bestPictureWinnerService);
        mockMvc = MockMvcBuilders.standaloneSetup(movieInfoService).build();
    }

    @Test
    public void testGetMovieInfo_ByTitleExistsInRepository_callsAPI_ReturnsMovieInfo() throws Exception {
        // Mocking input
        GetMovieInfoRequest request = new GetMovieInfoRequest();
        request.setTitle("The Matrix");

        MovieInfo movieInfo = new MovieInfo();
        movieInfo.setTitle("The Matrix");

        Optional<MovieInfo> movieInfoOptional = Optional.of(movieInfo);

        // Mocking behavior
        when(movieInfoRepository.findFirstByTitleContainingIgnoreCase(anyString()))
                .thenReturn(movieInfoOptional);
        when(movieInfoApiClient.getMovieFromAPIByTitle(anyString()))
                .thenReturn(movieInfo);
        when(bestPictureWinnerService.isBestPictureWinner(anyString(), anyString()))
                .thenReturn(true);

        // Call the API
        mockMvc.perform(get("/api?title={title}", request.getTitle()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is(request.getTitle())));
        verify(movieInfoRepository, times(1)).findFirstByTitleContainingIgnoreCase(request.getTitle());
        verify(movieInfoRepository, never()).findByImdbIDIgnoreCase(anyString());
        verify(movieInfoApiClient, never()).getMovieFromAPIByTitle(anyString());
        verify(bestPictureWinnerService, never())
                .isBestPictureWinner(anyString(), anyString());
    }

    @Test
    public void testGetMovieInfo_ByTitleExistsInRepository_ReturnsMovieInfo() {
        // Mocking input
        GetMovieInfoRequest request = new GetMovieInfoRequest();
        request.setTitle("The Matrix");

        MovieInfo movieInfo = new MovieInfo();
        movieInfo.setTitle("The Matrix");

        when(movieInfoRepository.findFirstByTitleContainingIgnoreCase(request.getTitle()))
                .thenReturn(Optional.of(movieInfo));

        // Mocking behavior
        when(bestPictureWinnerService.isBestPictureWinner(movieInfo.getYear(), movieInfo.getTitle()))
                .thenReturn(true);

        // Performing the method under test
        ResponseEntity<MovieInfo> response = movieInfoService.getMovieInfo(request);

        // Assertions
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(movieInfo, response.getBody());

        verify(movieInfoApiClient, never()).getMovieFromAPIByTitle(anyString());
        verify(movieInfoRepository, never()).save(any());
    }

    @Test
    public void testGetMovieInfo_ByTitleNotFoundInRepository_ReturnsMovieInfoFromAPI() {
        // Mocking input
        GetMovieInfoRequest request = new GetMovieInfoRequest();
        request.setTitle("The Matrix");

        MovieInfo movieInfoFromAPI = new MovieInfo();
        movieInfoFromAPI.setTitle("The Matrix");

        when(movieInfoRepository.findFirstByTitleContainingIgnoreCase(request.getTitle()))
                .thenReturn(Optional.empty());

        // Mocking behavior
        when(movieInfoApiClient.getMovieFromAPIByTitle(request.getTitle()))
                .thenReturn(movieInfoFromAPI);
        when(bestPictureWinnerService.isBestPictureWinner(movieInfoFromAPI.getYear(), movieInfoFromAPI.getTitle()))
                .thenReturn(true);
        when(movieInfoRepository.save(movieInfoFromAPI))
                .thenReturn(movieInfoFromAPI);

        // Performing the method under test
        ResponseEntity<MovieInfo> response = movieInfoService.getMovieInfo(request);

        // Assertions
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(movieInfoFromAPI, response.getBody());

        verify(movieInfoApiClient, times(1)).getMovieFromAPIByTitle(request.getTitle());
        verify(movieInfoRepository, times(1)).save(movieInfoFromAPI);
    }

    @Test
    public void testGetMovieInfo_ByImdbIdExistsInRepository_ReturnsMovieInfo() {
        // Mocking input
        GetMovieInfoRequest request = new GetMovieInfoRequest();
        request.setImdbID("tt0120338");

        MovieInfo movieInfo = new MovieInfo();
        movieInfo.setImdbID("tt0120338");

        when(movieInfoRepository.findByImdbIDIgnoreCase(request.getImdbID()))
                .thenReturn(Optional.of(movieInfo));

        // Mocking behavior
        when(bestPictureWinnerService.isBestPictureWinner(movieInfo.getYear(), movieInfo.getTitle()))
                .thenReturn(true);

        // Performing the method under test
        ResponseEntity<MovieInfo> response = movieInfoService.getMovieInfo(request);

        // Assertions
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(movieInfo, response.getBody());

        verify(movieInfoApiClient, never()).getMovieFromAPIByImdbID(anyString());
        verify(movieInfoRepository, never()).save(any());
    }

    @Test
    public void testGetMovieInfo_ByImdbIdNotFoundInRepository_ReturnsMovieInfoFromAPI() {
        // Mocking input
        GetMovieInfoRequest request = new GetMovieInfoRequest();
        request.setImdbID("tt0120338");

        MovieInfo movieInfoFromAPI = new MovieInfo();
        movieInfoFromAPI.setImdbID("tt0120338");

        when(movieInfoRepository.findByImdbIDIgnoreCase(request.getImdbID()))
                .thenReturn(Optional.empty());

        // Mocking behavior
        when(movieInfoApiClient.getMovieFromAPIByImdbID(request.getImdbID()))
                .thenReturn(movieInfoFromAPI);
        when(bestPictureWinnerService.isBestPictureWinner(movieInfoFromAPI.getYear(), movieInfoFromAPI.getTitle()))
                .thenReturn(true);
        when(movieInfoRepository.save(movieInfoFromAPI))
                .thenReturn(movieInfoFromAPI);

        // Performing the method under test
        ResponseEntity<MovieInfo> response = movieInfoService.getMovieInfo(request);

        // Assertions
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(movieInfoFromAPI, response.getBody());

        verify(movieInfoApiClient, times(1)).getMovieFromAPIByImdbID(request.getImdbID());
        verify(movieInfoRepository, times(1)).save(movieInfoFromAPI);
    }

    @Test
    public void testGetMovieInfo_BothTitleAndImdbIdMissing_ThrowsIllegalArgumentException() {
        // Mocking input
        GetMovieInfoRequest request = new GetMovieInfoRequest();

        // Performing the method under test and asserting the exception
        assertThrows(IllegalArgumentException.class, () -> movieInfoService.getMovieInfo(request));

        verify(movieInfoRepository, never()).findFirstByTitleContainingIgnoreCase(anyString());
        verify(movieInfoRepository, never()).findByImdbIDIgnoreCase(anyString());
        verify(movieInfoApiClient, never()).getMovieFromAPIByTitle(anyString());
        verify(movieInfoApiClient, never()).getMovieFromAPIByImdbID(anyString());
        verify(movieInfoRepository, never()).save(any());
    }
}
