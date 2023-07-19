package com.rasha.util;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BestPictureWinnerServiceImpl implements BestPictureWinnerService {

    private final ResourceLoader resourceLoader;
    @Value("${best.picture.winners.file.name}") private final String bestPictureWinnersFileName;
    private Map<String, String> bestPictureWinners;

    @Override
    public boolean isBestPictureWinner(String year, String title) {
        String winnerTitle = bestPictureWinners.get(year);
        return winnerTitle != null && winnerTitle.equalsIgnoreCase(title);
    }

    @PostConstruct
    public void init() {
        try {
            Resource resource = resourceLoader.getResource("classpath:" + bestPictureWinnersFileName);
            try(InputStream inputStream = resource.getInputStream();) {
                byte[] bytes = FileCopyUtils.copyToByteArray(inputStream);
                bestPictureWinners = Arrays.stream(new String(bytes, StandardCharsets.UTF_8).split("\n"))
                        .map(line -> line.split(","))
                        .collect(Collectors.toMap(line -> line[0], line -> line[1]));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}