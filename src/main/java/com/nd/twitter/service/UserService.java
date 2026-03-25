package com.nd.twitter.service;

import com.nd.twitter.exception.UserException;
import com.nd.twitter.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserService{
    public User findUserById(Long userId) throws UserException;
    public User findUserProfileByJwt(String jwt) throws UserException;
    public User updateUser(Long userId, User user) throws UserException;
    public User followUser(Long userId, User user) throws UserException;

    public List<User> searchUsers(String query);
}
