package chizhikov.utilitiesbot.bot.commands;


import chizhikov.utilitiesbot.application.DataManager;
import chizhikov.utilitiesbot.bot.userdata.ChatState;
import chizhikov.utilitiesbot.bot.userdata.Chats;
import chizhikov.utilitiesbot.database.Tariff;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.sql.SQLException;

public class GetActualTariffCommand extends AbstractCommand {
    public GetActualTariffCommand(String commandIdentifier, String description, Chats chats, DataManager dataManager) {
        super(commandIdentifier, description, chats, dataManager);
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        if (chats.getState(chat) == ChatState.MAIN) {
            try {
                Tariff actualTariff = dataManager.getActualTariff();
                sendAnswer(absSender, chat, getCommandIdentifier(), actualTariff.toString());
            } catch (SQLException sqlException) {
                sendAnswer(absSender, chat, getCommandIdentifier(), "Неизвестная ошибка при обращении к базе данных!");
                sendAnswer(absSender, chat, getCommandIdentifier(), sqlException.getMessage());
            }
        } else {
            sendWrongStateAnswer(absSender, chat);
        }

    }
}
