package chizhikov.utilitiesbot.bot.commands;


import chizhikov.utilitiesbot.bot.userdata.ChatState;
import chizhikov.utilitiesbot.bot.userdata.Chats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

@Component
public class AddTariffCommand extends AbstractCommand {

    @Autowired
    public AddTariffCommand(Chats chats) {
        super("add_tariff", "добавить новый тариф", chats);
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        if (chats.getState(chat) == ChatState.MAIN) {
            chats.updateState(chat, ChatState.ADD_T_DATE);
            sendAnswer(absSender, chat, getCommandIdentifier(), ChatState.ADD_T_DATE.message);
        } else {
            sendWrongStateAnswer(absSender, chat);
        }

    }
}
