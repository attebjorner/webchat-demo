package internal.loltech.webchat;

import java.time.LocalDateTime;

public record MessageDto(
        String content,
        String senderName,
        LocalDateTime sentTime
) {
}
