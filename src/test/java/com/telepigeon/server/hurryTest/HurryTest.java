package com.telepigeon.server.hurryTest;

import com.telepigeon.server.domain.Hurry;
import com.telepigeon.server.dto.hurry.HurryDto;
import com.telepigeon.server.repository.HurryRepository;
import com.telepigeon.server.service.hurry.HurryRetriever;
import com.telepigeon.server.service.hurry.HurrySaver;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class HurryTest {

    @Mock
    private HurryRepository hurryRepository = Mockito.mock(HurryRepository.class);

    @Test
    @DisplayName("hurry 생성")
    public void createHurryTest(){
        Hurry hurry = Hurry.create(1L, 2L);
        Assertions.assertNotNull(hurry);
    }

    @Test
    @DisplayName("hurry 생성 확인")
    public void checkCreateHurryTest(){
        Hurry hurry = Hurry.create(1L, 2L);
        Assertions.assertEquals(hurry.getRoomAndSender(), "1:2");
    }

    @Test
    @DisplayName("hurry DB에 저장 확인")
    public void checkHurryInDB(){
        HurrySaver hurrySaver = new HurrySaver(hurryRepository);
        Hurry hurry = Hurry.create(1L, 2L);
        Mockito.doAnswer(invocation -> hurry).when(hurryRepository).save(hurry);
        Hurry hurry1 = hurrySaver.save(hurry);
        Assertions.assertEquals(hurry.getRoomAndSender(), hurry1.getRoomAndSender());
    }

    @Test
    @DisplayName("hurry DB에서 꺼내오기 확인")
    public void checkHurryToDB(){
        HurryRetriever hurryRetriever = new HurryRetriever(hurryRepository);
        Hurry hurry = Hurry.create(1L, 2L);
        Mockito.doAnswer(invocation -> true).when(hurryRepository).existsById(hurry.getRoomAndSender());
        HurryDto hurryDto = HurryDto.of(hurry);
        boolean isCheck = hurryRetriever.existsByRoomIdAndSenderId(hurryDto.roomId(), hurryDto.senderId());
        Assertions.assertTrue(isCheck);
    }

}
