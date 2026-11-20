package com.audiosphere.api.dto;

import java.util.List;

public record DiscoveryHomeResponse(
    List<ArtistResponse> artists,
    List<TrackResponse> trendingTracks,
    String updatedAt
) {}