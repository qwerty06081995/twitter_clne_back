package com.nd.twitter.controller;

import com.nd.twitter.dto.LikeDto;
import com.nd.twitter.dto.mapper.LikeDtoMapper;
import com.nd.twitter.exception.TwitException;
import com.nd.twitter.exception.UserException;
import com.nd.twitter.model.Like;
import com.nd.twitter.model.User;
import com.nd.twitter.service.LikeService;
import com.nd.twitter.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LikeController {
    private final LikeService likeService;
    private final UserService userService;

    @PostMapping("/{twitId}/likes")
    public ResponseEntity<LikeDto> likeTwit(@PathVariable Long twitId, @RequestHeader("Authorization") String jwt) throws UserException, TwitException {
        User user = userService.findUserProfileByJwt(jwt);
        Like like = likeService.likeTwit(twitId, user);

        LikeDto likeDto = LikeDtoMapper.toLikeDto(like, user);

        return new ResponseEntity<>(likeDto,HttpStatus.CREATED);
    }

    @PostMapping("/twit/{twitId}")
    public ResponseEntity<List<LikeDto>> getAllLikes(@PathVariable Long twitId, @RequestHeader("Authorization") String jwt) throws UserException, TwitException {
        User user = userService.findUserProfileByJwt(jwt);
        List<Like> likeList = likeService.getAllLikes(twitId);

        List<LikeDto> likeDtoList = LikeDtoMapper.toLikeDtoList(likeList, user);

        return new ResponseEntity<>(likeDtoList,HttpStatus.OK);
    }
}
