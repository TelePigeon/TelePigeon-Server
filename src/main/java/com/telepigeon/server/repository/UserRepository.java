package com.telepigeon.server.repository;

import com.telepigeon.server.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsBySerialIdAndProvider(String serialId, String provider);

    Optional<User> findBySerialIdAndProvider(String serialId, String provider);
}
