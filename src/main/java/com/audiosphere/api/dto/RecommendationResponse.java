package com.audiosphere.api.dto;

public record RecommendationResponse(
        String id,
        String title,
        String artist,
        String artwork,
        String genre,
        String releaseYear,
        String previewUrl,
        String trackViewUrl,
        String youtubeSearchUrl,
        double score,
        String reason
) {}
