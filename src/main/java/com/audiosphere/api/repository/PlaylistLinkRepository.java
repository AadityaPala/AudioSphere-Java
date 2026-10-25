package com.audiosphere.api.repository;

import com.audiosphere.api.model.PlaylistLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PlaylistLinkRepository extends JpaRepository<PlaylistLink, Long> {
    List<PlaylistLink> findByUserId(String userId);

    @Transactional
    void deleteByUserIdAndUrl(String userId, String url);

    boolean existsByUserIdAndUrl(String userId, String url);
}