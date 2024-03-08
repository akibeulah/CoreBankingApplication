package com.seaico.corebankingapplication.mapper;

import com.seaico.corebankingapplication.dto.user.UserBasicProfileDto;
import com.seaico.corebankingapplication.dto.user.UserProfileDto;
import com.seaico.corebankingapplication.models.User;
//import org.mapstruct.Mapper;

//@Mapper
public class UserMapper {
    public static UserProfileDto userToUserProfileDto(User user) {
        return new UserProfileDto(
                user.getFullName(),
                user.getUsername(),
                user.getEmail(),
                user.getDateCreated(),
                user.getRole(),
                user.getActivities(),
                user.getAccounts()
        );
    }

    public static UserBasicProfileDto userToUserBasicProfileDto(User user) {
        return new UserBasicProfileDto(
                user.getId(),
                user.getFullName(),
                user.getUsername(),
                user.getEmail()
        );
    }
}
