package com.audiosphere.api.model;

import jakarta.persistence.*;

@Entity
@Table(name = "links")
public class PlaylistLink {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "url", nullable = false)
    private String url;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "album_art_url")
    private String albumArtUrl;

    @Column(name = "genre")
    private String genre;

    @Column(name = "release_year")
    private String releaseYear;

    @Column(name = "lyrics", columnDefinition = "TEXT")
    private String lyrics;

    @Column (name = "artist")
    private String artist;

    // Constructors, getters, and setters

    public PlaylistLink() {}

    public PlaylistLink(
            String userId,
            String url,
            String title,
            String albumArtUrl,
            String genre,
            String releaseYear,
            String lyrics,
            String artist) {
        this.userId = userId;
        this.url = url;
        this.title = title;
        this.albumArtUrl = albumArtUrl;
        this.genre = genre;
        this.releaseYear = releaseYear;
        this.lyrics = lyrics;
        this.artist = artist;
    }

    public Long getId() { return id; }
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getAlbumArtUrl() { return albumArtUrl; }
    public void setAlbumArtUrl(String albumArtUrl) { this.albumArtUrl = albumArtUrl; }
    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }
    public String getReleaseYear() { return releaseYear; }
    public void setReleaseYear(String releaseYear) { this.releaseYear = releaseYear; }
    public String getLyrics() { return lyrics; }
    public void setLyrics(String lyrics) { this.lyrics = lyrics; }
    public String getArtist() { return artist; }
    public void setArtist(String artist) { this.artist = artist; }
}