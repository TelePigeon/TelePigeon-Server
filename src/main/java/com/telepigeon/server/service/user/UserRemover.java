package com.telepigeon.server.service.user;

import com.telepigeon.server.domain.User;
import com.telepigeon.server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserRemover {

    private final UserRepository userRepository;

    public void remove(final User user) {
        userRepository.delete(user);
    }
}
