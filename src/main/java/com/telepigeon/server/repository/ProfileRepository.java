package com.telepigeon.server.repository;

import com.telepigeon.server.domain.Profile;
import com.telepigeon.server.domain.Room;
import com.telepigeon.server.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Optional<Profile> findAllByUserAndRoom(Users user, Room room);
    Optional<Profile> findAllByUserNotAndRoom(Users user, Room room);
    List<Profile> findAllByUserId(Long userId);
}
