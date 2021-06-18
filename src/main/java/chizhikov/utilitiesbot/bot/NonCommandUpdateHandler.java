package chizhikov.utilitiesbot.bot;

import chizhikov.utilitiesbot.bot.exceptions.MessageProcessingException;
import chizhikov.utilitiesbot.bot.noncommands.AbstractNonCommand;
import chizhikov.utilitiesbot.bot.userdata.ChatState;
import chizhikov.utilitiesbot.bot.userdata.Chats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class NonCommandUpdateHandler {
    private final Map<ChatState, AbstractNonCommand> nonCommandMap;
    private final Chats chats;

    @Autowired
    public NonCommandUpdateHandler(Set<AbstractNonCommand> nonCommandSet, Chats chats) {
        nonCommandMap = new HashMap<>();
        for (AbstractNonCommand nonCommand : nonCommandSet) {
            for (ChatState state : nonCommand.getStates()) {
                nonCommandMap.put(state, nonCommand);
            }
        }
        this.chats = chats;
    }

    public SendMessage process(Chat chat, String message) throws MessageProcessingException {
        try {
            return nonCommandMap.get(chats.getState(chat)).execute(chat, message);
        } catch (NullPointerException npe) {
            throw new MessageProcessingException("Не найден обработчик команды для состояния " + chats.getState(chat) + "!", npe);
        }
    }
}
