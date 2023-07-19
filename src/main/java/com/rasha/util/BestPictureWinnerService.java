package com.rasha.util;

public interface BestPictureWinnerService {

    boolean isBestPictureWinner(String year, String title);

    void init(); // For testing purpose
}
