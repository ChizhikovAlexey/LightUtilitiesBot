package chizhikov.utilitiesbot.bot.commands;

import chizhikov.utilitiesbot.bot.userdata.ChatState;
import chizhikov.utilitiesbot.bot.userdata.Chats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

@Component
public class Start extends AbstractCommand {

    @Autowired
    public Start(Chats chats) {
        super("start", "начало работы", chats);
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        if (chats.getState(chat) == ChatState.NONE) {
            chats.updateState(chat, ChatState.MAIN);
            sendAnswer(absSender, chat, getCommandIdentifier(), ChatState.MAIN.message);
        }
    }
}
