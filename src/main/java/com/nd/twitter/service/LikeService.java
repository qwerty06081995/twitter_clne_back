package com.nd.twitter.service;

import com.nd.twitter.exception.TwitException;
import com.nd.twitter.exception.UserException;
import com.nd.twitter.model.Like;
import com.nd.twitter.model.User;

import java.util.List;

public interface LikeService {
    public Like likeTwit(Long twitId, User user) throws UserException, TwitException;
    public List<Like> getAllLikes(Long twitId) throws TwitException;
}
