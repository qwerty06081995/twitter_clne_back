package com.nd.twitter.controller;

import com.nd.twitter.dto.UserDto;
import com.nd.twitter.dto.mapper.UserDtoMapper;
import com.nd.twitter.exception.UserException;
import com.nd.twitter.model.User;
import com.nd.twitter.service.UserService;
import com.nd.twitter.utils.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<UserDto> getUserProfile(@RequestHeader("Authorization") String jwt) throws UserException {
        User user = userService.findUserProfileByJwt(jwt);
        UserDto userDto = UserDtoMapper.toUserDto(user);
        userDto.setReq_user(true);
        return new ResponseEntity<>(userDto, HttpStatus.ACCEPTED);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long userId, @RequestHeader("Authorization") String jwt) throws UserException {
        User reqUser = userService.findUserProfileByJwt(jwt);
        User user = userService.findUserById(userId);
        UserDto userDto = UserDtoMapper.toUserDto(user);
        userDto.setReq_user(UserUtil.isReqUser(reqUser, user));
        userDto.setFollowed(UserUtil.isFollowedByReqUser(reqUser, user));
        return new ResponseEntity<>(userDto, HttpStatus.ACCEPTED);
    }

    @GetMapping("/search")
    public ResponseEntity<List<UserDto>> searchUser(@RequestParam String query, @RequestHeader("Authorization") String jwt) throws UserException {
        User reqUser = userService.findUserProfileByJwt(jwt);
        List<User> userList = userService.searchUsers(query);

        List<UserDto> userDtoList = UserDtoMapper.toUserDtoList(userList);

        return new ResponseEntity<>(userDtoList, HttpStatus.ACCEPTED);
    }

    @PutMapping("/update")
    public ResponseEntity<UserDto> updateUser(@RequestBody User req, @RequestHeader("Authorization") String jwt) throws UserException {
        User reqUser = userService.findUserProfileByJwt(jwt);
        User updatedUser = userService.updateUser(reqUser.getId(), req);
        UserDto userDto = UserDtoMapper.toUserDto(updatedUser);

        return new ResponseEntity<>(userDto, HttpStatus.ACCEPTED);
    }

    @PutMapping("/{userId}/follow")
    public ResponseEntity<UserDto> followUser(@PathVariable Long userId, @RequestHeader("Authorization") String jwt) throws UserException {
        User reqUser = userService.findUserProfileByJwt(jwt);
        User user = userService.followUser(userId, reqUser);
        UserDto userDto = UserDtoMapper.toUserDto(user);
        userDto.setFollowed(UserUtil.isFollowedByReqUser(reqUser, user));

        return new ResponseEntity<>(userDto, HttpStatus.ACCEPTED);
    }



}
