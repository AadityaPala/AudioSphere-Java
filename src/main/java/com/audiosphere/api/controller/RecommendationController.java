
package com.audiosphere.api.controller;

import com.audiosphere.api.dto.RecommendationResponse;
import com.audiosphere.api.service.RecommendationService;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recommendations")
public class RecommendationController {

    private final RecommendationService recommendationService;

    public RecommendationController(
            RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @GetMapping
    public List<RecommendationResponse> getRecommendations(
            @AuthenticationPrincipal Jwt jwt,
            @RequestParam(defaultValue = "12") int limit,
            @RequestParam(required = false) String genre) {

        return recommendationService.getRecommendations(
                jwt.getSubject(),
                limit,
                genre
        );
    }
}
