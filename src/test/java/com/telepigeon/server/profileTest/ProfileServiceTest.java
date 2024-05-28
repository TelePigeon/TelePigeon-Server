package com.telepigeon.server.profileTest;

import com.telepigeon.server.domain.Profile;
import com.telepigeon.server.domain.Room;
import com.telepigeon.server.domain.Users;
import com.telepigeon.server.dto.profile.response.ProfileInfoDto;
import com.telepigeon.server.dto.profile.response.ProfileKeywordDto;
import com.telepigeon.server.dto.type.AgeRange;
import com.telepigeon.server.dto.type.Gender;
import com.telepigeon.server.dto.type.Relation;
import com.telepigeon.server.service.profile.ProfileRetriever;
import com.telepigeon.server.service.profile.ProfileService;
import com.telepigeon.server.service.room.RoomRetriever;
import com.telepigeon.server.service.user.UserRetriever;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ProfileServiceTest {

    @MockBean
    private RoomRetriever roomRetriever;

    @MockBean
    private UserRetriever userRetriever;

    @MockBean
    private ProfileRetriever profileRetriever;

    @Autowired
    private ProfileService profileService;

    @Test
    @DisplayName("Keyword 가져와지는지 확인")
    public void getKeywordTest() {
        // Given
        Long userId = 1L;
        Long roomId = 1L;

        Users user = Mockito.mock(Users.class);
        Room room = Mockito.mock(Room.class);
        Profile profile = Mockito.mock(Profile.class);
        String keyword = "one,two,three";
        List<String> keywordList = Arrays.asList(keyword.split(","));

        when(profile.getKeywords()).thenReturn(keyword);
        when(roomRetriever.findById(roomId)).thenReturn(room);
        when(userRetriever.findById(userId)).thenReturn(user);
        when(profileRetriever.findByUserAndRoom(user, room)).thenReturn(profile);

        // When
        ProfileKeywordDto profileKeyword = profileService.getProfileKeyword(roomId, userId);

        // Then
        Assertions.assertThat(profileKeyword.keywords()).isEqualTo(keywordList);
    }

    @Test
    @DisplayName("방 키워드 추가 정보 가져오는 테스트")
    public void getProfileExtraInfoTest() {
        // Given
        Long userId = 1L;
        Long roomId = 1L;

        Users user = Mockito.mock(Users.class);
        Room room = Mockito.mock(Room.class);
        Profile profile = Mockito.mock(Profile.class);
        Gender gender = Gender.MALE;
        AgeRange ageRange = AgeRange.TWENTY;
        Relation relation = Relation.CHILD;

        when(profile.getGender()).thenReturn(gender);
        when(profile.getAgeRange()).thenReturn(ageRange);
        when(profile.getRelation()).thenReturn(relation);
        when(roomRetriever.findById(roomId)).thenReturn(room);
        when(userRetriever.findById(userId)).thenReturn(user);
        when(profileRetriever.findByUserAndRoom(user, room)).thenReturn(profile);

        // When
        ProfileInfoDto profileInfoDto = profileService.getProfileExtraInfo(roomId, userId);

        // Then
        Assertions.assertThat(profileInfoDto.gender()).isEqualTo(gender.getContent());
        Assertions.assertThat(profileInfoDto.ageRange()).isEqualTo(ageRange.getContent());
        Assertions.assertThat(profileInfoDto.relation()).isEqualTo(relation.getContent());
    }
}
