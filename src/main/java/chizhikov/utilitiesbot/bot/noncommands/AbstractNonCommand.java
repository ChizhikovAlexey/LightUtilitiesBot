package chizhikov.utilitiesbot.bot.noncommands;

import chizhikov.utilitiesbot.bot.exceptions.MessageProcessingException;
import chizhikov.utilitiesbot.bot.userdata.ChatState;
import chizhikov.utilitiesbot.bot.userdata.Chats;
import chizhikov.utilitiesbot.data.DataManager;
import org.telegram.telegrambots.meta.api.objects.Chat;

import java.util.HashSet;
import java.util.Set;


public abstract class AbstractNonCommand {
    protected final Chats chats;
    protected final DataManager dataManager;
    protected Set<ChatState> states;

    public AbstractNonCommand(Chats chats, DataManager dataManager) {
        this.chats = chats;
        this.dataManager = dataManager;
        states = new HashSet<>();
    }

    public abstract String execute(Chat chat, String text) throws MessageProcessingException;

    public Set<ChatState> getStates() {
        return states;
    }
}
