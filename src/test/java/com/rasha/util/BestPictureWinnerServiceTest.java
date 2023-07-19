package com.rasha.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BestPictureWinnerServiceTest {

    @Mock
    private ResourceLoader resourceLoader;

    @Mock
    private Resource resource;

    private BestPictureWinnerService bestPictureWinnerService;

    @BeforeEach
    public void setup() throws IOException {
        MockitoAnnotations.openMocks(this);
        bestPictureWinnerService = new BestPictureWinnerServiceImpl(resourceLoader, "winners.csv");

        byte[] fileData = "1999,The Matrix\n2000,Gladiator\n".getBytes(StandardCharsets.UTF_8);

        when(resourceLoader.getResource("classpath:winners.csv")).thenReturn(resource);
        when(resource.getInputStream()).thenReturn(new ByteArrayInputStream(fileData));

        bestPictureWinnerService.init();
    }

    @Test
    public void testIsBestPictureWinner_ValidYearAndTitle_ReturnsTrue() {
        assertTrue(bestPictureWinnerService.isBestPictureWinner("1999", "The Matrix"));
    }

    @Test
    public void testIsBestPictureWinner_InvalidYear_ReturnsFalse() {
        assertFalse(bestPictureWinnerService.isBestPictureWinner("2001", "Gladiator"));
    }

    @Test
    public void testIsBestPictureWinner_InvalidTitle_ReturnsFalse() {
        assertFalse(bestPictureWinnerService.isBestPictureWinner("1999", "Gladiator"));
    }

    @Test
    public void testIsBestPictureWinner_NullYear_ReturnsFalse() {
        assertFalse(bestPictureWinnerService.isBestPictureWinner(null, "The Matrix"));
    }

    @Test
    public void testIsBestPictureWinner_NullTitle_ReturnsFalse() {
        assertFalse(bestPictureWinnerService.isBestPictureWinner("1999", null));
    }

    @Test
    public void testIsBestPictureWinner_EmptyYear_ReturnsFalse() {
        assertFalse(bestPictureWinnerService.isBestPictureWinner("", "The Matrix"));
    }

    @Test
    public void testIsBestPictureWinner_EmptyTitle_ReturnsFalse() {
        assertFalse(bestPictureWinnerService.isBestPictureWinner("1999", ""));
    }
}
