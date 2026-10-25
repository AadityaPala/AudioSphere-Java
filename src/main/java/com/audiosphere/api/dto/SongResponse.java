package com.audiosphere.api.dto;

public record SongResponse(
    String url,
    String title,
    String albumArtUrl,
    String genre,
    String releaseYear,
    String lyrics
) {}