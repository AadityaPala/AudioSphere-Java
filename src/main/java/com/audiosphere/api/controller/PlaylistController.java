package com.audiosphere.api.controller;

import com.audiosphere.api.dto.AddSongRequest;
import com.audiosphere.api.dto.SongResponse;
import com.audiosphere.api.model.PlaylistLink;
import com.audiosphere.api.repository.PlaylistLinkRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/links")
public class PlaylistController {

    private final PlaylistLinkRepository repository;

    public PlaylistController(PlaylistLinkRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<SongResponse> getPlaylists(@AuthenticationPrincipal Jwt jwt) {
        String userId = jwt.getSubject();

        return repository.findByUserId(userId).stream()
                .map(PlaylistController::toSongResponse)
                .collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<SongResponse> addSong(
            @AuthenticationPrincipal Jwt jwt,
            @Valid @RequestBody AddSongRequest request) {

        String userId = jwt.getSubject();

        if (repository.existsByUserIdAndUrl(userId, request.url())) {
            return ResponseEntity.badRequest().build();
        }

        PlaylistLink newLink = new PlaylistLink(
                userId,
                request.url(),
                request.title(),
                request.artist(),
                request.albumArtUrl(),
                request.genre(),
                request.releaseYear(),
                request.lyrics());

        repository.save(newLink);

        return ResponseEntity.ok(toSongResponse(newLink));
    }

    private static SongResponse toSongResponse(PlaylistLink link) {
        return new SongResponse(
                link.getUrl(),
                link.getTitle(),
                link.getAlbumArtUrl(),
                link.getGenre(),
                link.getReleaseYear(),
                link.getLyrics());
    }

    @DeleteMapping
    public ResponseEntity<Void> removeSong(
            @AuthenticationPrincipal Jwt jwt,
            @RequestParam("url") String url) {

        String userId = jwt.getSubject();
        repository.deleteByUserIdAndUrl(userId, url);

        return ResponseEntity.ok().build();
    }
}
