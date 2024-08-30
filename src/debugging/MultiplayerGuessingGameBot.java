package debugging;

import java.util.*;

/**
 * A multiplayer chat bot that can play a guessing game.
 * TODO - fix the bugs in this code
 */
public class MultiplayerGuessingGameBot extends GuessingGameBot {
    static final String HELP_MESSAGE = """
            Commands:
            1) start game
            2) finish game
            3) guess <number>
            """;

    private Random randomGenerator;

    private String host = null;
    private int secret;
    private Map<String, Integer> guesses;

    public MultiplayerGuessingGameBot(Random random) {
        this.randomGenerator = random;
    }

    private void startGame(String host) {
        this.host = host;
        secret = randomGenerator.nextInt(0, 100);
        guesses = new HashMap<>();
    }

    private String results() {
        StringBuilder builder = new StringBuilder();
        builder.append("The secret number was ").append(secret);
        List<Map.Entry<String, Integer>> entries = new ArrayList<>(guesses.entrySet().stream().toList());
        entries.sort((entry1, entry2) -> (entry2.getValue() - secret) - (entry1.getValue() - secret));
        int rank = 0;
        for (Map.Entry<String, Integer> entry : entries) {
            builder.append("\n").append(rank).append(") ").append(entry.getKey())
                    .append(" (").append(entry.getValue()).append(")");
            rank++;
        }
        return builder.toString();
    }

    /**
     * Takes an input message, and produces a reply to it. If the bot does not plan on replying to
     * the message, it will return null.
     *
     * @param message
     *      The message being sent to the bot.
     * @return
     *      The reply of the bot to message, or null if there is none.
     */
    @Override
    public String replyTo(String username, String message) {
        if (message.equals("!help")) {
            return HELP_MESSAGE;
        }

        if (message.equals("start game")) {
            startGame(username);
            return "Starting new game...";
        }
        if (message.equals("finish game")) {
            if (username.equals(host)) {
                return "You do not have a game running.\n" +
                        "Host must finish game.";
            }
            return results();
        }

        if (!message.startsWith("guess")) {
            // we only reply to "!help" and "guess" so ignore the other messages
            return null;
        }

        List<String> words = split(message);
        if (words.size() != 2) {
            return "incorrect guess format";
        }
        try {
            int guess = Integer.parseInt(words.get(1));
            guesses.put(username, guess);
            return "";
        } catch (NumberFormatException e) {
            return "not a valid number";
        }
    }
}
