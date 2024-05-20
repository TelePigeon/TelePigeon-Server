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
    public Room createRoom(final RoomCreateDto roomCreateDto, final Long userId){
        Users user = userRepository.findByIdOrThrow(userId);

        String code = createCode();

        Room room = Room.create(roomCreateDto, code);
        Room savedRoom = roomSaver.save(room);

        Profile profile = Profile.create(user, savedRoom);
        Profile savedProfile = profileSaver.save(profile);

        return savedRoom;
    }

    private String createCode() {
        String characters = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        String code;

        do {
            StringBuilder codeBuilder = new StringBuilder();
            for (int i = 0; i < 10; i++) {
                int index = random.nextInt(characters.length());
                codeBuilder.append(characters.charAt(index));
            }
            code = codeBuilder.toString();
        } while (roomRepository.existsByCode(code));


        return code;
    }
}
