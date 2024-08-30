package debugging;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MockBotTest {
    private ChatBot bot;

    @Before
    public void setup() {
        bot = new MockBot();
    }

    @Test
    public void testABC() {
        assertEquals("abc", bot.replyTo("you", "abc"));
        assertEquals("abc", bot.replyTo("you", "ABC"));
    }

    @Test
    public void testXYZ() {
        assertEquals("XYZ", bot.replyTo("you", "xyz"));
        assertEquals("XYZ", bot.replyTo("you", "XYZ"));
    }

    @Test
    public void testABCXYZ() {
        assertEquals("abcXYZ", bot.replyTo("you", "abcxyz"));
        assertEquals("abcXYZ", bot.replyTo("you", "ABCXYZ"));
    }

    @Test
    public void testHelloWorld() {
        assertEquals("hello WoRld", bot.replyTo("you", "hello world"));
    }

    @Test
    public void testAlternatingCaps() {
        assertEquals("alTeRnaTing caPS", bot.replyTo("you", "aLtErNaTiNg CaPs"));
        assertEquals("alTeRnaTing caPS", bot.replyTo("you", "alternating caps"));
    }

    @Test
    public void testEmpty() {
        assertEquals("", bot.replyTo("you", ""));
    }

    @Test
    public void testMock() {
        assertEquals("iTS a mock boT WoRld afTeR all", bot.replyTo("you", "its a mock bot world after all"));
    }
}
