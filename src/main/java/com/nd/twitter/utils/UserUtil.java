package com.nd.twitter.utils;

import com.nd.twitter.model.User;

public class UserUtil {
    public static boolean isReqUser(User reqUser, User user){
        return reqUser.getId().equals(user.getId());
    }

    public static boolean isFollowedByReqUser(User reqUser, User user){
        return reqUser.getFollowings().contains(user);
    }
}
