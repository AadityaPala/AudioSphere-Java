
package com.audiosphere.api.service;

import com.audiosphere.api.dto.ArtistResponse;
import com.audiosphere.api.dto.ArtistTracksResponse;
import com.audiosphere.api.dto.DiscoveryHomeResponse;
import com.audiosphere.api.dto.TrackResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.util.List;

@Service
public class DiscoveryService {

    private final RestClient pythonClient;

    public DiscoveryService(
            RestClient.Builder restClientBuilder,

            @Value("${python.service.url:http://localhost:8000}")
            String pythonServiceUrl) {

        this.pythonClient = restClientBuilder
                .baseUrl(pythonServiceUrl)
                .build();
    }

    // --------------------------------------------------
    // HOME DISCOVERY
    // --------------------------------------------------

    public DiscoveryHomeResponse getHomeDiscovery() {

        try {

            return pythonClient.get()
                    .uri("/discovery/home")
                    .retrieve()
                    .body(DiscoveryHomeResponse.class);

        } catch (RestClientException exception) {

            throw new DiscoveryServiceException(
                    "Unable to retrieve home discovery data",
                    exception
            );
        }
    }

    // --------------------------------------------------
    // POPULAR ARTISTS
    // --------------------------------------------------

    public List<ArtistResponse> getPopularArtists(int limit) {

        try {

            List<ArtistResponse> response =
                    pythonClient.get()
                            .uri(uriBuilder -> uriBuilder
                                    .path("/discovery/artists")
                                    .queryParam("limit", limit)
                                    .build())
                            .retrieve()
                            .body(
                                new ParameterizedTypeReference<
                                    List<ArtistResponse>
                                >() {}
                            );

            return response != null ? response : List.of();

        } catch (RestClientException exception) {

            throw new DiscoveryServiceException(
                    "Unable to retrieve popular artists",
                    exception
            );
        }
    }

    // --------------------------------------------------
    // TRENDING TRACKS
    // --------------------------------------------------

    public List<TrackResponse> getTrendingTracks(int limit) {

        try {

            List<TrackResponse> response =
                    pythonClient.get()
                            .uri(uriBuilder -> uriBuilder
                                    .path("/discovery/tracks")
                                    .queryParam("limit", limit)
                                    .build())
                            .retrieve()
                            .body(
                                new ParameterizedTypeReference<
                                    List<TrackResponse>
                                >() {}
                            );

            return response != null ? response : List.of();

        } catch (RestClientException exception) {

            throw new DiscoveryServiceException(
                    "Unable to retrieve trending tracks",
                    exception
            );
        }
    }

    // --------------------------------------------------
    // ARTIST DETAILS
    // --------------------------------------------------

    public ArtistResponse getArtist(String artistId) {

        try {

            return pythonClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/discovery/artists/{artistId}")
                            .build(artistId))
                    .retrieve()
                    .body(ArtistResponse.class);

        } catch (RestClientException exception) {

            throw new DiscoveryServiceException(
                    "Unable to retrieve artist details",
                    exception
            );
        }
    }

    // --------------------------------------------------
    // ARTIST TOP TRACKS
    // --------------------------------------------------

    public ArtistTracksResponse getArtistTracks(
            String artistId,
            int limit) {

        try {

            return pythonClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/discovery/artists/{artistId}/tracks")
                            .queryParam("limit", limit)
                            .build(artistId))
                    .retrieve()
                    .body(ArtistTracksResponse.class);

        } catch (RestClientException exception) {

            throw new DiscoveryServiceException(
                    "Unable to retrieve artist tracks",
                    exception
            );
        }
    }
}