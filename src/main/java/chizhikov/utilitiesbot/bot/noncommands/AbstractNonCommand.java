package chizhikov.utilitiesbot.bot.noncommands;

import chizhikov.utilitiesbot.data.DataManager;
import chizhikov.utilitiesbot.bot.exceptions.MessageProcessingException;
import chizhikov.utilitiesbot.bot.userdata.Chats;
import org.telegram.telegrambots.meta.api.objects.Chat;

public abstract class AbstractNonCommand {
    protected final Chats chats;
    protected final DataManager dataManager;

    public AbstractNonCommand(Chats chats, DataManager dataManager) {
        this.chats = chats;
        this.dataManager = dataManager;
    }

    public abstract String execute (Chat chat, String text) throws MessageProcessingException;
}
