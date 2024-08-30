package debugging;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class GuessingGameBotTest {
    private ChatBot bot;

    @Before
    public void setup() {
        this.bot = new GuessingGameBot();
    }

    @Test
    public void testInvalidCloseToHelp() {
        // first check it doesn't reply to messages like help
        assertNull(bot.replyTo("you", "help"));
        assertNull(bot.replyTo("you", "!help me"));
        assertNull(bot.replyTo("you", "/help"));
    }

    @Test
    public void testHelp() {
        assertEquals(GuessingGameBot.HELP_MESSAGE, bot.replyTo("you", "!help"));
    }

    @Test
    public void testAboveZero() {
        assertEquals("yes", bot.replyTo("you", "guess higher 0"));
    }

    @Test
    public void testBelow100() {
        assertEquals("yes", bot.replyTo("you", "guess lower 100"));
    }

    @Test
    public void testAbove50() {
        assertEquals("no", bot.replyTo("you", "guess higher 50"));
    }

    @Test
    public void testAbove25() {
        assertEquals("yes", bot.replyTo("you", "guess higher 25"));
    }

    @Test
    public void testAbove37() {
        assertEquals("yes", bot.replyTo("you", "guess higher 37"));
    }

    @Test
    public void testAbove43() {
        assertEquals("no", bot.replyTo("you", "guess higher 43"));
    }

    @Test
    public void testAbove40() {
        assertEquals("yes", bot.replyTo("you", "guess higher 40"));
    }

    @Test
    public void testEqual41() {
        assertEquals("you lose!", bot.replyTo("you", "guess equal 41"));
    }

    @Test
    public void testEqual42() {
        assertEquals("you win!", bot.replyTo("you", "guess equal 42"));
    }
}
