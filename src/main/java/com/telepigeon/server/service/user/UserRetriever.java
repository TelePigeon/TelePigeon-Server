package com.telepigeon.server.service.user;

import com.telepigeon.server.domain.Users;
import com.telepigeon.server.exception.NotFoundException;
import com.telepigeon.server.exception.code.NotFoundErrorCode;
import com.telepigeon.server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserRetriever {
    private final UserRepository userRepository;

    public Users findById(final long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(NotFoundErrorCode.USER_NOT_FOUND));
    }
}
