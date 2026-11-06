package com.audiosphere.api.dto;

import jakarta.validation.constraints.NotBlank;

public record AddSongRequest(
    @NotBlank(message = "URL cannot be blank")
    String url,

    @NotBlank(message = "Title cannot be blank")
    String title,

    String artist,
    String albumArtUrl,
    String genre,
    String releaseYear,
    String lyrics
) {}
