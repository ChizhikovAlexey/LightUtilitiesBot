package chizhikov.utilitiesbot.bot.commands;


import chizhikov.utilitiesbot.bot.userdata.ChatState;
import chizhikov.utilitiesbot.bot.userdata.Chats;
import chizhikov.utilitiesbot.data.DataManager;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.sql.SQLException;

public class GetActualShortReport extends AbstractCommand {
    public GetActualShortReport(String commandIdentifier, String description, Chats chats, DataManager dataManager) {
        super(commandIdentifier, description, chats, dataManager);
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        if (chats.getState(chat) == ChatState.MAIN) {
            try {
                String shortReport = dataManager.getActualShortRepot();
                sendAnswer(absSender, chat, getCommandIdentifier(), shortReport);
            } catch (SQLException sqlException) {
                sendAnswer(absSender, chat, getCommandIdentifier(), "Неизвестная ошибка при обращении к базе данных!");
                sendAnswer(absSender, chat, getCommandIdentifier(), sqlException.getMessage());
            }
        } else {
            sendWrongStateAnswer(absSender, chat);
        }
    }
}
