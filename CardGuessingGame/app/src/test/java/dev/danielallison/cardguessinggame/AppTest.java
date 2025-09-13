package dev.danielallison.cardguessinggame;

import org.junit.Test;
import static org.junit.Assert.*;

import dev.danielallison.cardguessinggame.App;

public class AppTest {
    @Test public void appHasAGreeting() {
        App classUnderTest = new App();
        assertNotNull("app should have a greeting", classUnderTest.getGreeting());
    }
}
