package internal.loltech.webchat;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static internal.loltech.webchat.WebSocketConfig.TOPIC;

@RestController
public class ChatController {

    private final MessageService messageService;

    public ChatController(final MessageService messageService) {
        this.messageService = messageService;
    }

    @MessageMapping("/send")
    @SendTo(TOPIC + "/main-chat")
    public MessageDto sendMessage(final MessageDto dto) {
        var message = new Message(
                dto.content(),
                dto.senderName(),
                dto.sentTime()
        );
        messageService.saveMessage(message);
        return dto;
    }

    @GetMapping("/messages")
    public List<MessageDto> loadMessages() {
        return messageService.listAllMessages().stream()
                .map(it -> new MessageDto(it.getContent(), it.getSenderName(), it.getSentTime()))
                .toList();
    }
}
