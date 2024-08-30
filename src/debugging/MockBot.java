package debugging;

/**
 * The mock bot will respond to any message with the message repeated in alternating caps.
 * The case of a letter is determined by its position in the alphabet,
 * letters before 'O' (including 'O') will be lowercase and after 'O' will be uppercase.
 */
public class MockBot implements ChatBot {
    /**
     * Respond to any message with the message in mock case.
     */
    @Override
    public String replyTo(String username, String message) {
        return mock(message);
    }

    private String mock(String message) {
        StringBuilder result = new StringBuilder();
        for (char letter : message.toUpperCase().toCharArray()) {
            if (letter > 'O') {
                result.append(Character.toUpperCase(letter));
            } else {
                result.append(Character.toLowerCase(letter));
            }
        }
        return result.toString();
    }
}
