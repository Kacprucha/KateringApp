package com.kateringapp.backend;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DummyUnitTest {

    @Test
    public void dummyTest() {

        //given
        int number = -2;

        //when
        int absoluteNumber = Math.abs(number);

        //then
        Assertions.assertEquals(2, absoluteNumber);
    }
}
