package debugging;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CommitBotTest {
    ChatBot bot;

    @Before
    public void setup() {
        bot = new CommitBot();
    }

    @Test
    public void testHelp() {
        assertEquals(CommitBot.HELP_MESSAGE, bot.replyTo("you", "commit help"));
    }

    @Test
    public void testNewMessage() {
        bot = new CommitBot("https://whatthecommit.com/cc3fb5d2aa5eb2c21ea707df706689ae/index.txt");
        String commit = "If it's hacky and you know it clap you hands (clap clap)! ";
        assertEquals(commit, bot.replyTo("you", "commit new"));
    }

    @Test
    public void testSaveMessage() {
        String commit = bot.replyTo("you", "commit new");
        assertEquals("Commit saved", bot.replyTo("you", "commit save"));
        assertEquals(commit, bot.replyTo("you", "commit load"));
    }

    @Test
    public void testNoLast() {
        assertEquals("No last message to save", bot.replyTo("you", "commit save"));
    }

    @Test
    public void testNoSave() {
        String commit = bot.replyTo("you", "commit new");
        assertEquals("No saved message to load", bot.replyTo("you", "commit load"));
    }

    @Test
    public void testLoadTwice() {
        String commit = bot.replyTo("you", "commit new");
        assertEquals("Commit saved", bot.replyTo("you", "commit save"));
        bot.replyTo("you", "commit new");
        assertEquals(commit, bot.replyTo("you", "commit load"));
    }
}
