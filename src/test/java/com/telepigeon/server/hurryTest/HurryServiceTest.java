package com.telepigeon.server.hurryTest;

import com.telepigeon.server.domain.Hurry;
import com.telepigeon.server.repository.HurryRepository;
import com.telepigeon.server.service.hurry.HurryRetriever;
import com.telepigeon.server.service.hurry.HurrySaver;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

public class HurryServiceTest {

    @Mock
    private HurryRepository hurryRepository = Mockito.mock(HurryRepository.class);

    @Test
    @DisplayName("hurry DB에 저장 확인")
    public void checkHurryInDB(){
        HurrySaver hurrySaver = new HurrySaver(hurryRepository);
        Hurry hurry = Hurry.create("1");
        Mockito.doAnswer(invocation -> hurry).when(hurryRepository).save(hurry);
        Hurry hurry1 = hurrySaver.save(hurry);
        Assertions.assertEquals(hurry.getProfileId(), hurry1.getProfileId());
    }

    @Test
    @DisplayName("hurry DB에서 꺼내오기 확인")
    public void checkHurryToDB(){
        HurryRetriever hurryRetriever = new HurryRetriever(hurryRepository);
        Hurry hurry = Hurry.create("1");
        Mockito.doAnswer(invocation -> true).when(hurryRepository).existsById(hurry.getProfileId());
        boolean isCheck = hurryRetriever.existsByProfileId(1L);
        Assertions.assertTrue(isCheck);
    }
}
