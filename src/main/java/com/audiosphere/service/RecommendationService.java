
package com.audiosphere.service;

import com.audiosphere.api.dto.RecommendationRequest;
import com.audiosphere.api.dto.RecommendationResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.util.List;
import java.util.Objects;

@Service
public class RecommendationService {

    private static final Logger logger =
            LoggerFactory.getLogger(RecommendationService.class);

    private final RestClient restClient;

    public RecommendationService(
            RestClient.Builder restClientBuilder,
            @Value("${python.service.url:http://localhost:8000}")
            String pythonServiceUrl
    ) {
        Objects.requireNonNull(
                restClientBuilder,
                "RestClient.Builder must not be null"
        );

        Objects.requireNonNull(
                pythonServiceUrl,
                "Python service URL must not be null"
        );

        this.restClient = restClientBuilder
                .baseUrl(pythonServiceUrl)
                .build();
    }

    public List<RecommendationResponse> getRecommendations(
            RecommendationRequest request
    ) {

        if (request == null ||
                request.seeds() == null ||
                request.seeds().isEmpty()) {
            return List.of();
        }

        try {
            logger.info(
                    "Requesting recommendations for {} seed tracks",
                    request.seeds().size()
            );

            RecommendationResponse[] response = restClient.post()
                    .uri("/recommendations")
                    .contentType(
                            Objects.requireNonNull(
                                    MediaType.APPLICATION_JSON
                            )
                    )
                    .accept(MediaType.APPLICATION_JSON)
                    .body(request)
                    .retrieve()
                    .body(RecommendationResponse[].class);

            if (response == null || response.length == 0) {
                logger.info("Python returned no recommendations");
                return List.of();
            }

            logger.info(
                    "Received {} recommendations from Python",
                    response.length
            );

            return List.of(response);

        } catch (RestClientException exception) {

            logger.error(
                    "Failed to fetch recommendations from Python",
                    exception
            );

            throw new RecommendationServiceException(
                    "Unable to generate recommendations at this time",
                    exception
            );
        }
    }
}
