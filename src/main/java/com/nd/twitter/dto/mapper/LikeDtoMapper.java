package com.nd.twitter.dto.mapper;

import com.nd.twitter.dto.LikeDto;
import com.nd.twitter.dto.TwitDto;
import com.nd.twitter.dto.UserDto;
import com.nd.twitter.model.Like;
import com.nd.twitter.model.User;

import java.util.ArrayList;
import java.util.List;

public class LikeDtoMapper {

    public static LikeDto toLikeDto(Like like, User reqUser) {
        UserDto userDto = UserDtoMapper.toUserDto(like.getUser());
        UserDto reqUserDto = UserDtoMapper.toUserDto(reqUser);
        TwitDto twitDto = TwitDtoMapper.toTwitDto(like.getTwit(), reqUser);

        LikeDto likeDto = new LikeDto();
        likeDto.setId(like.getId());
        likeDto.setUser(userDto);
        likeDto.setTwit(twitDto);
        return likeDto;
    }

    public static List<LikeDto> toLikeDtoList(List<Like> likes, User reqUser) {
        List<LikeDto> likeDtoList = new ArrayList<>();
        for (Like like : likes) {
            UserDto userDto = UserDtoMapper.toUserDto(like.getUser());
            TwitDto twitDto = TwitDtoMapper.toTwitDto(like.getTwit(), reqUser);

            LikeDto likeDto = new LikeDto();
            likeDto.setId(like.getId());
            likeDto.setUser(userDto);
            likeDto.setTwit(twitDto);
            likeDtoList.add(likeDto);
        }
        return likeDtoList;
    }
}
