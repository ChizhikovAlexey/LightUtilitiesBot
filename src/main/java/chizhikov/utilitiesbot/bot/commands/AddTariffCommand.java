package chizhikov.utilitiesbot.bot.commands;


import chizhikov.utilitiesbot.application.DataManager;
import chizhikov.utilitiesbot.bot.userdata.ChatState;
import chizhikov.utilitiesbot.bot.userdata.Chats;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

public class AddTariffCommand extends AbstractCommand {
    public AddTariffCommand(String commandIdentifier, String description, Chats chats, DataManager dataManager) {
        super(commandIdentifier, description, chats, dataManager);
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
