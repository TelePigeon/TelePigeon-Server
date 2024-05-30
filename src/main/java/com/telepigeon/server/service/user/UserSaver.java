package com.telepigeon.server.service.user;

import com.telepigeon.server.domain.User;
import com.telepigeon.server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserSaver {

    private final UserRepository userRepository;

    public User save(final User user){
        return userRepository.save(user);
    }

}
