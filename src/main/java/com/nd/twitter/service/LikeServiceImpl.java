package com.nd.twitter.service;

import com.nd.twitter.exception.TwitException;
import com.nd.twitter.exception.UserException;
import com.nd.twitter.model.Like;
import com.nd.twitter.model.Twit;
import com.nd.twitter.model.User;
import com.nd.twitter.repository.LikeRepository;
import com.nd.twitter.repository.TwitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {
    public final LikeRepository likeRepository;
    public final TwitService twitService;
    public final TwitRepository twitRepository;

    @Override
    public Like likeTwit(Long twitId, User user) throws UserException, TwitException {
        Like isLikeExist = likeRepository.isLikeExists(user.getId(), twitId);
        if (isLikeExist != null) {
            likeRepository.deleteById(isLikeExist.getId());
            return isLikeExist;
        }
        Twit twit = twitService.findTwitById(twitId);

        Like like = new Like();
        like.setTwit(twit);
        like.setUser(user);

        Like savedLike = likeRepository.save(like);

        twit.getLikes().add(like);
        twitRepository.save(twit);

        return savedLike;
    }

    @Override
    public List<Like> getAllLikes(Long twitId) throws TwitException {
        Twit twit = twitService.findTwitById(twitId);
        return likeRepository.findByTwitId(twitId);
    }
}
