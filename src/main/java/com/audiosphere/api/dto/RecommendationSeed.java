package com.audiosphere.api.dto;

public record RecommendationSeed(
    String title,
    String artist,
    String genre,
    String releaseYear
) {}
