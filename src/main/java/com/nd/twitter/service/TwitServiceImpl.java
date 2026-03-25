package com.nd.twitter.service;

import com.nd.twitter.exception.TwitException;
import com.nd.twitter.exception.UserException;
import com.nd.twitter.model.Twit;
import com.nd.twitter.model.User;
import com.nd.twitter.repository.TwitRepository;
import com.nd.twitter.request.TwitReplyRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TwitServiceImpl implements TwitService {
    public final TwitRepository twitRepository;

    @Override
    public Twit createTwit(Twit request, User user) throws UserException {
        Twit twit = new Twit();
        twit.setContent(request.getContent());
        twit.setCreatedAt(LocalDateTime.now());
        twit.setImage(request.getImage());
        twit.setUser(user);
        twit.setReply(false);
        twit.setTwit(true);
        twit.setVideo(request.getVideo());
        return twitRepository.save(twit);
    }

    @Override
    public List<Twit> findAllTwits() {
        return twitRepository.findAllByIsTwitTrueOrderByCreatedAtDesc();
    }

    @Override
    public Twit reTweet(Long twitId, User user) throws UserException, TwitException {
        Twit twit = findTwitById(twitId);
        if (twit.getReTwitUser().contains(user)){
            twit.getReTwitUser().remove(user);
        }else{
            twit.getReTwitUser().add(user);
        }
        return twitRepository.save(twit);
    }

    @Override
    public Twit findTwitById(Long twitId) throws TwitException {
        return twitRepository.findById(twitId)
                .orElseThrow(()-> new TwitException("Twit not found with id: " + twitId));
    }

    @Override
    public void deleteTwitById(Long twitId, Long userId) throws TwitException, UserException {
        Twit twit = findTwitById(twitId);
        if (!userId.equals(twit.getUser().getId())) {
            throw new TwitException("you can not delete another user's twit");
        }
        twitRepository.deleteById(twit.getId());
    }

    @Override
    public Twit removeFromReTweet(Long twitId, Long userId) throws TwitException, UserException {

        return null;
    }

    @Override
    public Twit createReply(TwitReplyRequest request, User user) throws TwitException {
        Twit replyFor = findTwitById(request.getTwitId());

        Twit twit = new Twit();
        twit.setContent(request.getContent());
        twit.setCreatedAt(LocalDateTime.now());
        twit.setImage(request.getImage());
        twit.setUser(user);
        twit.setReply(true);
        twit.setTwit(false);
        twit.setReplyFor(replyFor);

        Twit savedReply = twitRepository.save(twit);
        twit.getReplyTwits().add(savedReply);
        twitRepository.save(replyFor);

        return replyFor;
    }

    @Override
    public List<Twit> getUserTwits(User user) {
        return twitRepository.findByReTwitUserContainsOrUser_IdAndIsTwitTrueOrderByCreatedAtDesc(user, user.getId());
    }

    @Override
    public List<Twit> findByLikesContainsUser(User user) {
        return twitRepository.findByLikesUser_Id(user.getId());
    }
}
