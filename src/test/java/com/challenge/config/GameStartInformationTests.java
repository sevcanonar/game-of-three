package com.challenge.config;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

@SpringBootTest
public class GameStartInformationTests {
    public boolean isStarted = false;
    @InjectMocks
    GameStartInformation gameStartInformation;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(gameStartInformation, "isStarted", isStarted);
    }

    @Test
    public void testSetGetInstance() {
        Assert.assertEquals(false, gameStartInformation.getInstance());
        gameStartInformation.setInstance(true);
        Assert.assertEquals(true, gameStartInformation.getInstance());
    }
}
