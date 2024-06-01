package internal.loltech.webchat;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public final class ContentHelper {

    public static String encodeContent(String content) {
        return Base64.getEncoder().encodeToString(content.getBytes(StandardCharsets.UTF_8));
    }

    public static void decodeContent(Message message) {
        byte[] decoded = Base64.getDecoder().decode(message.getContent());
        message.setContent(new String(decoded, StandardCharsets.UTF_8));
    }
}
