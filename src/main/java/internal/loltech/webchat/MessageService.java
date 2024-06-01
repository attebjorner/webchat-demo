package internal.loltech.webchat;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

import static internal.loltech.webchat.ContentHelper.encodeContent;

@Service
public class MessageService {

    private final MessageRepo messageRepo;

    public MessageService(final MessageRepo messageRepo) {
        this.messageRepo = messageRepo;
    }

    public void saveMessage(final Message message) {
        var encodedContent = encodeContent(message.getContent());
        message.setContent(encodedContent);
        messageRepo.save(message);
    }

    public List<Message> listAllMessages() {
        var messages = messageRepo.findAll(Sort.by("sentTime"));
        messages.forEach(ContentHelper::decodeContent);
        return messages;
    }
}
