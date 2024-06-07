package com.telepigeon.server.repository;

import com.telepigeon.server.domain.Profile;
import com.telepigeon.server.domain.Worry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WorryRepository extends JpaRepository<Worry, Long> {
    List<Worry> findAllByProfile(Profile profile);

    @Query("SELECT w FROM Worry w WHERE w.times LIKE %:time%")
    List<Worry> findAllByTime(String time);
}
