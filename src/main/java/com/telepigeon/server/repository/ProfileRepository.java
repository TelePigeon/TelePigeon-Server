package com.telepigeon.server.repository;

import com.telepigeon.server.domain.Profile;
import com.telepigeon.server.domain.Room;
import com.telepigeon.server.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Optional<Profile> findByUserAndRoom(User user, Room room);
    Optional<Profile> findByUserNotAndRoom(User user, Room room);
}
