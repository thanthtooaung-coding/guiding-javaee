package org.vinn.mapper;

import org.vinn.dto.UserDto;
import org.vinn.model.User;

public class UserMapper {
    public static UserDto toDto(User user){
        return new UserDto(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }
}
