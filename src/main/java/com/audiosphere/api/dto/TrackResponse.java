package com.audiosphere.api.dto;

public record TrackResponse(
    String id,
    String title,
    String artist,
    String album,
    String artwork,
    Integer duration,
    String youtubeVideoId,
    Boolean playable,
    String source
) {}