package chizhikov.utilitiesbot.bot.userdata;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Chat;

import java.util.HashMap;
import java.util.Map;

@Component("Chats")
public class ChatsManager {
    private final Map<String, ChatState> chatsMap;

    public ChatsManager() {
        chatsMap = new HashMap<>();
    }

    public ChatState getState(String id) {
        return chatsMap.getOrDefault(id, ChatState.NOT_STARTED);
    }

    public ChatState getState(Chat chat) {
        return chatsMap.getOrDefault(chat.getId().toString(), ChatState.NOT_STARTED);
    }

    public void setState(Chat chat, ChatState state) {
        chatsMap.put(chat.getId().toString(), state);
    }
}
