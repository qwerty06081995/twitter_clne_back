package com.nd.twitter.service;

import com.nd.twitter.exception.TwitException;
import com.nd.twitter.exception.UserException;
import com.nd.twitter.model.Twit;
import com.nd.twitter.model.User;
import com.nd.twitter.request.TwitReplyRequest;

import java.util.List;

public interface TwitService {
    public Twit createTwit(Twit request, User user) throws UserException;
    public List<Twit> findAllTwits();
    public Twit reTweet(Long twitId, User user) throws UserException, TwitException;
    public Twit findTwitById(Long twitId) throws TwitException;

    public void deleteTwitById(Long twitId, Long userId) throws TwitException, UserException;
    public Twit removeFromReTweet(Long twitId, Long userId) throws TwitException, UserException;
    public Twit createReply(TwitReplyRequest request, User user) throws TwitException;
    public List<Twit> getUserTwits(User user);
    public List<Twit> findByLikesContainsUser(User user);
}
