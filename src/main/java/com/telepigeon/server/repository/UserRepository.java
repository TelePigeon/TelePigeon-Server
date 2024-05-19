package com.telepigeon.server.repository;

import com.telepigeon.server.domain.Users;
import com.telepigeon.server.exception.NotFoundException;
import com.telepigeon.server.exception.code.NotFoundErrorCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Long> {
    default Users findByIdOrThrow(Long id) {
        return findById(id).orElseThrow(() -> new NotFoundException(NotFoundErrorCode.USER_NOT_FOUND));
    }
}
