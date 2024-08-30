package debugging;

import java.util.List;
import java.util.ArrayList;

/**
 * A basic chat bot that can play a guessing game.
 * TODO - fix the bugs in this code
 */
public class GuessingGameBot implements ChatBot {
    private static final int SECRET_NUMBER = 42;
    static final String HELP_MESSAGE = """
            Guessing game bot!
            There are 3 options
            1) guess higher <number>
            2) guess lower <number>
            3) guess equal <number>
            """;

    /**
     * Splits a string into a list of words, separated by space characters (' ').
     *
     * @param input The string being entered.
     * @return The words that input consists of, after being split.
     */
    protected static List<String> split(String input) {
        List<String> output = new ArrayList<>();
        int index = 0, newIndex;

        while (index != -1) {
            newIndex = input.indexOf(' ', index + 1);
            if (newIndex == -1) {
                output.add(input.substring(index));
                break;
            } else {
                output.add(input.substring(index, newIndex));
            }
            index = newIndex + 1;
        }

        return output;
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
        if (!message.startsWith("guess")) {
            // we only reply to "!help" and "guess" so ignore the other messages
            return null;
        }

        List<String> words = split(message);
        if (words.size() != 3) {
            return "incorrect guess format";
        }
        String type = words.get(1);
        switch (type) {
            case "higher" -> {
                try {
                    int guess = Integer.parseInt(words.get(2));
                    return guess < SECRET_NUMBER ? "yes" : "no";
                } catch (NumberFormatException e) {
                    return "not a valid number";
                }
            }
            case "lower" -> {
                try {
                    int guess = Integer.parseInt(words.get(2));
                    return guess > SECRET_NUMBER ? "yes" : "no";
                } catch (NumberFormatException e) {
                    return "not a valid number";
                }
            }
            case "equal" -> {
                try {
                    int guess = Integer.parseInt(words.get(2));
                    return guess == SECRET_NUMBER ? "you win!" : "you lose!";
                } catch (NumberFormatException e) {
                    return "not a valid number";
                }
            }
            default -> {
                return "incorrect guess format";
            }
        }
    }
}
