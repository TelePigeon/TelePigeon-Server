package com.telepigeon.server.service.user;

import com.telepigeon.server.domain.User;
import com.telepigeon.server.exception.NotFoundException;
import com.telepigeon.server.exception.code.NotFoundErrorCode;
import com.telepigeon.server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserRetriever {

    private final UserRepository userRepository;

    public boolean existBySerialIdAndProvider(final String serialId, final String provider) {
        return userRepository.existsBySerialIdAndProvider(serialId, provider);
    }

    public User findBySerialIdAndProvider(final String serialId, final String provider) {
        return userRepository.findBySerialIdAndProvider(serialId, provider)
                .orElseThrow(() -> new NotFoundException(NotFoundErrorCode.USER_NOT_FOUND));
    }

    public User findById(final Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(NotFoundErrorCode.USER_NOT_FOUND));
    }
}
