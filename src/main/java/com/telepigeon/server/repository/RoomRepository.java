package com.telepigeon.server.repository;

import com.telepigeon.server.domain.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
    boolean existsByName(String name);
}
