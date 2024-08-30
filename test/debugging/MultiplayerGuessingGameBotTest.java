package debugging;

import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class MultiplayerGuessingGameBotTest {
    private ChatBot bot;

    @Before
    public void setup() {
        this.bot = new MultiplayerGuessingGameBot(new Random(2002));
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
        assertEquals(MultiplayerGuessingGameBot.HELP_MESSAGE, bot.replyTo("you", "!help"));
    }

    @Test
    public void testFinishWithoutStarting() {
        assertEquals("You do not have a game running.\nHost must finish game.", bot.replyTo("you", "finish game"));
    }

    @Test
    public void testFinishDifferentUser() {
        assertEquals("Starting new game...", bot.replyTo("you", "start game"));
        assertEquals("You do not have a game running.\nHost must finish game.", bot.replyTo("other", "finish game"));
    }

    @Test
    public void testNoGuesses() {
        assertEquals("Starting new game...", bot.replyTo("you", "start game"));
        assertEquals("The secret number was 68", bot.replyTo("you", "finish game"));
    }

    @Test
    public void testOneGuess() {
        assertEquals("Starting new game...", bot.replyTo("you", "start game"));
        assertEquals("", bot.replyTo("you", "guess 12"));
        assertEquals("The secret number was 68\n1) you (12)", bot.replyTo("you", "finish game"));
    }

    @Test
    public void testTwoGuess() {
        assertEquals("Starting new game...", bot.replyTo("you", "start game"));
        assertEquals("", bot.replyTo("you", "guess 12"));
        assertEquals("", bot.replyTo("other", "guess 60"));
        assertEquals("The secret number was 68\n1) other (60)\n2) you (12)", bot.replyTo("you", "finish game"));
    }

    @Test
    public void testMultipleGuess() {
        assertEquals("Starting new game...", bot.replyTo("you", "start game"));
        assertEquals("", bot.replyTo("you", "guess 12"));
        assertEquals("", bot.replyTo("root", "guess 5"));
        assertEquals("", bot.replyTo("admin", "guess 100"));
        assertEquals("", bot.replyTo("test", "guess 34"));
        assertEquals("The secret number was 68\n" +
                "1) admin (100)\n" +
                "2) test (34)\n" +
                "3) you (12)\n" +
                "4) root (5)", bot.replyTo("you", "finish game"));
    }

}
