package com.nd.twitter.controller;

import com.nd.twitter.dto.TwitDto;
import com.nd.twitter.dto.mapper.TwitDtoMapper;
import com.nd.twitter.exception.TwitException;
import com.nd.twitter.exception.UserException;
import com.nd.twitter.model.Twit;
import com.nd.twitter.model.User;
import com.nd.twitter.request.TwitReplyRequest;
import com.nd.twitter.response.ApiResponse;
import com.nd.twitter.service.TwitService;
import com.nd.twitter.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/twits")
@RequiredArgsConstructor
public class TwitController {
    public final TwitService twitService;
    public final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<TwitDto> createTwit(@RequestBody Twit reqTwit, @RequestHeader("Authorization") String jwt)
            throws UserException, TwitException
    {
        User user = userService.findUserProfileByJwt(jwt);

        Twit twit = twitService.createTwit(reqTwit, user);

        TwitDto twitDto = TwitDtoMapper.toTwitDto(twit, user);

        return new ResponseEntity<>(twitDto, HttpStatus.CREATED);
    }

    @PostMapping("/reply")
    public ResponseEntity<TwitDto> replyTwit(@RequestBody TwitReplyRequest replyRequestTwit, @RequestHeader("Authorization") String jwt)
            throws UserException, TwitException
    {
        User user = userService.findUserProfileByJwt(jwt);

        Twit twit = twitService.createReply(replyRequestTwit, user);

        TwitDto twitDto = TwitDtoMapper.toTwitDto(twit, user);

        return new ResponseEntity<>(twitDto, HttpStatus.CREATED);
    }

    @PutMapping("/{twitId}/reTwit")
    public ResponseEntity<TwitDto> reTwit(@PathVariable Long twitId, @RequestHeader("Authorization") String jwt)
            throws UserException, TwitException
    {
        User user = userService.findUserProfileByJwt(jwt);

        Twit twit = twitService.reTweet(twitId, user);

        TwitDto twitDto = TwitDtoMapper.toTwitDto(twit, user);

        return new ResponseEntity<>(twitDto, HttpStatus.OK);
    }

    @GetMapping("/{twitId}")
    public ResponseEntity<TwitDto> findTwitById(@PathVariable Long twitId, @RequestHeader("Authorization") String jwt)
            throws UserException, TwitException
    {
        User user = userService.findUserProfileByJwt(jwt);

        Twit twit = twitService.findTwitById(twitId);

        TwitDto twitDto = TwitDtoMapper.toTwitDto(twit, user);

        return new ResponseEntity<>(twitDto, HttpStatus.OK);
    }

    @DeleteMapping("/{twitId}")
    public ResponseEntity<ApiResponse> deleteTwit(@PathVariable Long twitId, @RequestHeader("Authorization") String jwt)
            throws UserException, TwitException
    {
        User user = userService.findUserProfileByJwt(jwt);

        twitService.deleteTwitById(twitId, user.getId());

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("Twit deleted successfully");
        apiResponse.setStatus(true);

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<TwitDto>> getAllTwits(@RequestHeader("Authorization") String jwt)
            throws UserException, TwitException
    {
        User user = userService.findUserProfileByJwt(jwt);

        List<Twit> twitList = twitService.findAllTwits();

        List<TwitDto> twitDtoList = TwitDtoMapper.toTwitDtoList(twitList, user);

        return new ResponseEntity<>(twitDtoList, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TwitDto>> getUsersAllTwit(@PathVariable Long userId, @RequestHeader("Authorization") String jwt)
            throws UserException, TwitException
    {
        User user = userService.findUserProfileByJwt(jwt);

        List<Twit> twitList = twitService.getUserTwits(user);

        List<TwitDto> twitDtoList = TwitDtoMapper.toTwitDtoList(twitList, user);

        return new ResponseEntity<>(twitDtoList, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}/likes")
    public ResponseEntity<List<TwitDto>> findTwitByLikesContainsUser(@PathVariable Long userId, @RequestHeader("Authorization") String jwt)
            throws UserException, TwitException
    {
        User user = userService.findUserProfileByJwt(jwt);

        List<Twit> twitList = twitService.findByLikesContainsUser(user);

        List<TwitDto> twitDtoList = TwitDtoMapper.toTwitDtoList(twitList, user);

        return new ResponseEntity<>(twitDtoList, HttpStatus.OK);
    }
}
