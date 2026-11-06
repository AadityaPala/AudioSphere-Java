package com.audiosphere.api.dto;

import java.util.List;

public record RecommendationRequest(
    List<RecommendationSeed> seeds,
    int limit
) {}