package com.telepigeon.server.hurryTest;

import com.telepigeon.server.domain.Hurry;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class HurryDomainTest {

    @Test
    @DisplayName("hurry 생성")
    public void createHurryTest(){
        Hurry hurry = Hurry.create("1");
        Assertions.assertNotNull(hurry);
    }

    @Test
    @DisplayName("hurry 생성 확인")
    public void checkCreateHurryTest(){
        Hurry hurry = Hurry.create("1");
        Assertions.assertEquals(hurry.getProfileId(), "1");
    }
}
