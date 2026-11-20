package com.audiosphere.api.dto;

import java.util.List;

public record ArtistTracksResponse(
    ArtistResponse artist,
    List<TrackResponse> tracks
) {}