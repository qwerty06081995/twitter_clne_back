package com.nd.twitter.utils;

import com.nd.twitter.model.Like;
import com.nd.twitter.model.Twit;
import com.nd.twitter.model.User;

public class TwitUtil {
    public static boolean isLikedByReqUser(User reqUser, Twit twit) {
        for (Like like : twit.getLikes()) {
            if (like.getUser().getId().equals(reqUser.getId())) {
                return true;
            }
        }
        return false;
    }
    public static boolean isReTwitByReqUser(User reqUser, Twit twit) {
        for (User user: twit.getReTwitUser()){
            if (user.getId().equals(reqUser.getId())) {
                return true;
            }
        }
        return false;
    }
}
