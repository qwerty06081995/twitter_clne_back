package com.nd.twitter.repository;

import com.nd.twitter.model.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LikeRepository extends JpaRepository<Like, Long> {

    @Query("SELECT l FROM Like l WHERE l.user.id=:userId AND l.twit.id=:twitId")
    public Like isLikeExists(@Param("userId") Long userId, @Param("twitId") Long twitId);

    @Query("SELECT l FROM Like l WHERE l.twit.id=:twitId")
    public List<Like> findByTwitId(@Param("twitId") Long twitId);
}
