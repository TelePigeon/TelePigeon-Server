package com.telepigeon.server.repository;

import com.telepigeon.server.domain.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {
    boolean existsByName(String name);
    boolean existsByCode(String code);

    Optional<Room> findAllByCode(String code);

}
