package chizhikov.utilitiesbot.bot.commands;

import chizhikov.utilitiesbot.bot.userdata.ChatState;
import chizhikov.utilitiesbot.bot.userdata.Chats;
import chizhikov.utilitiesbot.data.DataManager;
import chizhikov.utilitiesbot.data.entities.MonthData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.sql.SQLException;

@Component
public class DeleteActualMonthData extends AbstractCommand {
    private final DataManager dataManager;

    @Autowired
    public DeleteActualMonthData(Chats chats, DataManager dataManager) {
        super("delete_actual_month_data", "удалить данный за последний месяц", chats);
        this.dataManager = dataManager;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        if (chats.getState(chat) == ChatState.MAIN) {
            try {
                MonthData monthData = dataManager.deleteActualMonthData();
                sendAnswer(absSender, chat, getCommandIdentifier(), "Отчёт от " + monthData.getDate() + " был удалён!");
            } catch (SQLException sqlException) {
                sendAnswer(absSender, chat, getCommandIdentifier(), "Неизвестная ошибка при обращении к базе данных!");
                sendAnswer(absSender, chat, getCommandIdentifier(), sqlException.getMessage());
            }
        } else {
            sendWrongStateAnswer(absSender, chat);
        }
    }
}
