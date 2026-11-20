package com.audiosphere.api.dto;

public record ArtistResponse(
    String id,
    String name,
    String image,
    long listeners,
    long playcount,
    String source
) {}
