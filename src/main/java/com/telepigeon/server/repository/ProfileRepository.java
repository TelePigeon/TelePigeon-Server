package com.telepigeon.server.repository;

import com.telepigeon.server.domain.Profile;
import com.telepigeon.server.domain.Room;
import com.telepigeon.server.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

    Optional<Profile> findByUserAndRoom(User user, Room room);
    Optional<Profile> findByUserNotAndRoom(User user, Room room);
    boolean existsByUserNotAndRoom(User user, Room room);

    boolean existsByUserAndRoom(User user, Room room);
    List<Profile> findAllByUserId(Long userId);

    @Query("select count(p) from Profile p where p.room = :room and p.isDeleted = :isDeleted")
    int countByRoomAndIsDeleted(Room room, boolean isDeleted);
}
