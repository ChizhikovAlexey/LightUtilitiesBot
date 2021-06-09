package chizhikov.utilitiesbot.bot;

import chizhikov.utilitiesbot.bot.exceptions.MessageProcessingException;
import chizhikov.utilitiesbot.bot.noncommands.AbstractNonCommand;
import chizhikov.utilitiesbot.bot.userdata.ChatState;
import chizhikov.utilitiesbot.bot.userdata.Chats;
import org.telegram.telegrambots.meta.api.objects.Chat;

import java.util.Map;

public class NonCommandUpdateHandler {
    private final Map<ChatState, AbstractNonCommand> nonCommandMap;
    private final Chats chats;

    public NonCommandUpdateHandler(Map<ChatState, AbstractNonCommand> nonCommandMap, Chats chats) {
        this.nonCommandMap = nonCommandMap;
        this.chats = chats;
    }

    public String process (Chat chat, String message) throws MessageProcessingException {
        try {
            return nonCommandMap.get(chats.getState(chat)).execute(chat, message);
        } catch (NullPointerException npe) {
            throw new MessageProcessingException("Не найден обработчик команды для состояния " + chats.getState(chat) + "!", npe);
        }
    }
}
