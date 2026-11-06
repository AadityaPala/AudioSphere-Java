
package com.audiosphere.api.service;

import com.audiosphere.api.dto.RecommendationRequest;
import com.audiosphere.api.dto.RecommendationResponse;
import com.audiosphere.api.dto.RecommendationSeed;
import com.audiosphere.api.model.PlaylistLink;
import com.audiosphere.api.repository.PlaylistLinkRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;

@Service
public class RecommendationService {

    private final PlaylistLinkRepository playlistRepository;
    private final RestClient pythonClient;

    public RecommendationService(
            PlaylistLinkRepository playlistRepository,
            RestClient.Builder restClientBuilder,
            @Value("${python.service.url:http://localhost:8000}")
            String pythonServiceUrl) {

        this.playlistRepository = playlistRepository;
        this.pythonClient = restClientBuilder
                .baseUrl(pythonServiceUrl)
                .build();
    }

    public List<RecommendationResponse> getRecommendations(
            String userId,
            int requestedLimit) {

        int limit = Math.max(1, Math.min(requestedLimit, 30));

        // Retrieve only this authenticated user's saved tracks.
        List<PlaylistLink> savedTracks =
                playlistRepository.findByUserId(userId);

        // Python requires at least one recommendation seed.
        if (savedTracks.isEmpty()) {
            return List.of();
        }

        List<RecommendationSeed> seeds = savedTracks.stream()
                .map(track -> new RecommendationSeed(
                        track.getTitle(),
                        null, // PlaylistLink currently has no artist field.
                        track.getGenre(),
                        track.getReleaseYear()
                ))
                .toList();

        RecommendationRequest request =
                new RecommendationRequest(seeds, limit);

        try {
            List<RecommendationResponse> response =
                    pythonClient.post()
                            .uri("/recommendations")
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(request)
                            .retrieve()
                            .body(
                                new org.springframework.core.ParameterizedTypeReference<
                                        List<RecommendationResponse>>() {}
                            );

            return response != null ? response : List.of();

        } catch (RestClientException exception) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_GATEWAY,
                    "Recommendation service is unavailable: "
                            + exception.getMessage(),
                    exception
            );
        }
    }
}
