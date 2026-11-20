
package com.audiosphere.api.controller;

import com.audiosphere.api.dto.ArtistResponse;
import com.audiosphere.api.dto.ArtistTracksResponse;
import com.audiosphere.api.dto.DiscoveryHomeResponse;
import com.audiosphere.api.dto.TrackResponse;

import com.audiosphere.api.service.DiscoveryService;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/discovery")
public class DiscoveryController {

    private final DiscoveryService discoveryService;

    public DiscoveryController(
            DiscoveryService discoveryService) {

        this.discoveryService = discoveryService;
    }

    // GET /discovery/home

    @GetMapping("/home")
    public DiscoveryHomeResponse getHome() {

        return discoveryService.getHomeDiscovery();
    }

    // GET /discovery/artists?limit=12

    @GetMapping("/artists")
    public List<ArtistResponse> getArtists(
            @RequestParam(defaultValue = "12") int limit) {

        return discoveryService.getPopularArtists(
                Math.max(1, Math.min(limit, 30))
        );
    }

    // GET /discovery/tracks?limit=12

    @GetMapping("/tracks")
    public List<TrackResponse> getTracks(
            @RequestParam(defaultValue = "12") int limit) {

        return discoveryService.getTrendingTracks(
                Math.max(1, Math.min(limit, 30))
        );
    }

    // GET /discovery/artists/{artistId}

    @GetMapping("/artists/{artistId}")
    public ArtistResponse getArtist(
            @PathVariable String artistId) {

        return discoveryService.getArtist(artistId);
    }

    // GET /discovery/artists/{artistId}/tracks?limit=10

    @GetMapping("/artists/{artistId}/tracks")
    public ArtistTracksResponse getArtistTracks(
            @PathVariable String artistId,
            @RequestParam(defaultValue = "10") int limit) {

        return discoveryService.getArtistTracks(
                artistId,
                Math.max(1, Math.min(limit, 10))
        );
    }

    // GET /discovery/search?q=...&limit=10
    @GetMapping("/search")
    public Map<String, Object> search(
            @RequestParam String q,
            @RequestParam(defaultValue = "10") int limit) {

        return discoveryService.search(q, Math.max(1, Math.min(limit, 20)));
    }

    // GET /discovery/resolve?artist=...&title=...
    @GetMapping("/resolve")
    public Map<String, Object> resolve(
            @RequestParam String artist,
            @RequestParam String title) {

        return discoveryService.resolve(artist, title);
    }

}
