package com.telepigeon.server.repository;

import com.telepigeon.server.domain.Profile;
import com.telepigeon.server.domain.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {
    boolean existsByName(String name);
    boolean existsByCode(String code);

    Optional<Room> findByCode(String code);

}
