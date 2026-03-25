package com.nd.twitter.dto.mapper;

import com.nd.twitter.dto.TwitDto;
import com.nd.twitter.dto.UserDto;
import com.nd.twitter.model.Twit;
import com.nd.twitter.model.User;
import com.nd.twitter.utils.TwitUtil;

import java.util.ArrayList;
import java.util.List;

public class TwitDtoMapper {
    public static TwitDto toTwitDto(Twit twit, User reqUser) {
        UserDto userDto = UserDtoMapper.toUserDto(twit.getUser());
        boolean isLiked = TwitUtil.isLikedByReqUser(reqUser, twit);
        boolean isReTwit = TwitUtil.isReTwitByReqUser(reqUser, twit);

        List<Long> reTwitUserId = new ArrayList<>();

        for (User user1: twit.getReTwitUser()){
            reTwitUserId.add(user1.getId());
        }

        TwitDto twitDto = new TwitDto();
        twitDto.setId(twit.getId());
        twitDto.setContent(twit.getContent());
        twitDto.setCreatedAt(twit.getCreatedAt());
        twitDto.setImage(twit.getImage());
        twitDto.setTotalLikes(twit.getLikes().size());
        twitDto.setTotalReplies(twit.getReplyTwits().size());
        twitDto.setTotalReTwits(twit.getReTwitUser().size());
        twitDto.setUserDto(userDto);
        twitDto.setLiked(isLiked);
        twitDto.setReTwit(isReTwit);
        twitDto.setReTwitUsersId(reTwitUserId);
        twitDto.setReplyTwits(toTwitDtoList(twit.getReplyTwits(), reqUser));
        twitDto.setVideo(twit.getVideo());

        return twitDto;
    }

    public static List<TwitDto> toTwitDtoList(List<Twit> twits, User reqUser) {
        List<TwitDto> twitDtoList = new ArrayList<>();
        for (Twit twit: twits){
            TwitDto twitDto = toReplyTwitDto(twit, reqUser);
            twitDtoList.add(twitDto);
        }
        return twitDtoList;
    }

    private static TwitDto toReplyTwitDto(Twit twit, User reqUser) {
        UserDto userDto = UserDtoMapper.toUserDto(twit.getUser());
        boolean isLiked = TwitUtil.isLikedByReqUser(reqUser, twit);
        boolean isReTwit = TwitUtil.isReTwitByReqUser(reqUser, twit);

        List<Long> reTwitUserId = new ArrayList<>();

        for (User user1: twit.getReTwitUser()){
            reTwitUserId.add(user1.getId());
        }

        TwitDto twitDto = new TwitDto();
        twitDto.setId(twit.getId());
        twitDto.setContent(twit.getContent());
        twitDto.setCreatedAt(twit.getCreatedAt());
        twitDto.setImage(twit.getImage());
        twitDto.setTotalLikes(twit.getLikes().size());
        twitDto.setTotalReplies(twit.getReplyTwits().size());
        twitDto.setTotalReTwits(twit.getReTwitUser().size());
        twitDto.setUserDto(userDto);
        twitDto.setLiked(isLiked);
        twitDto.setReTwit(isReTwit);
        twitDto.setReTwitUsersId(reTwitUserId);

        twitDto.setVideo(twit.getVideo());

        return twitDto;
    }
}
