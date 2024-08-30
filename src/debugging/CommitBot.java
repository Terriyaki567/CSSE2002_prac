package debugging;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CommitBot implements ChatBot {
    static final String HELP_MESSAGE = """
            Commit bot
            Commands:
            1) commit new: generate a new commit message
            2) commit save: save last generated commit message
            3) commit load: load last saved commit message
            """;

    private final Map<String, String> lastMessages = new HashMap<>();
    private final Map<String, String> savedMessages = new HashMap<>();

    private final String url;

    public CommitBot(String url) {
        this.url = url;
    }

    public CommitBot() {
        this("https://whatthecommit.com/index.txt");
    }

    @Override
    public String replyTo(String username, String message) {
        if (!message.startsWith("commit")) {
            return null;
        }

        if (message.endsWith("help")) {
            return HELP_MESSAGE;
        }

        if (message.endsWith("load")) {
            if (!lastMessages.containsKey(username)) {
                return "No saved message to load";
            }
            return lastMessages.get(username);
        }

        if (message.endsWith("save")) {
            if (!lastMessages.containsKey(username)) {
                return "No last message to save";
            }
            savedMessages.put(username, lastMessages.get(username));
            return "Commit saved";
        }

        if (message.endsWith("new")) {
            try {
                String commit = generateMessage();
                return commit;
            } catch (IOException | URISyntaxException e) {
                return "IOException trying to fetch commit message";
            }
        }

        return "Unknown commit command";
    }

    private String generateMessage() throws IOException, URISyntaxException {
        StringBuilder result = new StringBuilder();

        URL url = new URI(this.url).toURL();
        Scanner scanner = new Scanner(url.openStream(), StandardCharsets.UTF_8);
        while (scanner.hasNext()) {
            result.append(scanner.next()).append(" ");
        }
        return result.toString();
    }
}
