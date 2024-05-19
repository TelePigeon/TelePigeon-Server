package com.telepigeon.server.service.room;

import com.telepigeon.server.domain.Profile;
import com.telepigeon.server.domain.Room;
import com.telepigeon.server.domain.Users;
import com.telepigeon.server.dto.room.request.RoomCreateDto;
import com.telepigeon.server.repository.RoomRepository;
import com.telepigeon.server.repository.UserRepository;
import com.telepigeon.server.service.profile.ProfileSaver;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;
    private final RoomSaver roomSaver;
    private final RoomRetriever roomRetriever;
    private final RoomRemover roomRemover;
    private final UserRepository userRepository;
    private final ProfileSaver profileSaver;

    @Transactional
    public void createRoom(RoomCreateDto roomCreateDto){
//        Users user = userRepository.findByIdOrThrow(userId);

        String characters = "abcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder codeBuilder = new StringBuilder();

        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            int index = random.nextInt(characters.length());
            codeBuilder.append(characters.charAt(index));
        }

        String code = codeBuilder.toString();

        Room room = Room.create(roomCreateDto, code);
        Room savedRoom = roomSaver.save(room);

//        Profile profile = Profile.create(user, savedRoom);
//        Profile savedProfile = profileSaver.save(profile);
    }
}
