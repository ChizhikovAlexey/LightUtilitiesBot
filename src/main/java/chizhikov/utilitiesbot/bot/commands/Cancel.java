package chizhikov.utilitiesbot.bot.commands;

import chizhikov.utilitiesbot.bot.userdata.ChatState;
import chizhikov.utilitiesbot.bot.userdata.Chats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

@Component
public class Cancel extends AbstractCommand {

    @Autowired
    public Cancel(Chats chats) {
        super("cancel", "прервать обработку команды", chats);
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        if (chats.getState(chat) != ChatState.NONE && chats.getState(chat) != ChatState.MAIN) {
            chats.updateState(chat, ChatState.MAIN);
            sendAnswer(absSender, chat, getCommandIdentifier(),
                    "Выполнение предыдущей команды прервано. Изменения не были записаны в базу данных.\n");
        } else {
            sendAnswer(absSender, chat, getCommandIdentifier(), "Нечего отменять!");
        }
        sendAnswer(absSender, chat, getCommandIdentifier(), ChatState.MAIN.message);
    }
}
