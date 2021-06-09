package chizhikov.utilitiesbot.bot.noncommands;

import chizhikov.utilitiesbot.application.DataManager;
import chizhikov.utilitiesbot.bot.exceptions.MessageProcessingException;
import chizhikov.utilitiesbot.bot.userdata.ChatState;
import chizhikov.utilitiesbot.bot.userdata.Chats;
import chizhikov.utilitiesbot.database.Tariff;
import org.telegram.telegrambots.meta.api.objects.Chat;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

public class AddTariffNonCommand extends AbstractNonCommand{
    private final Map<Chat, Tariff> tempTariffMap;

    public AddTariffNonCommand(Chats chats, DataManager dataManager) {
        super(chats, dataManager);
        tempTariffMap = new HashMap<>();
    }

    @Override
    public String execute(Chat chat, String text) throws MessageProcessingException {
        ChatState chatState = chats.getState(chat);
        switch (chatState) {
            case ADD_T_DATE -> {
                try {
                    tempTariffMap.put(chat, new Tariff());
                    tempTariffMap.get(chat).setDate(LocalDate.parse(text));
                    chats.updateState(chat, ChatState.ADD_T_ELECTRICITY);
                } catch (DateTimeParseException exc) {
                    throw new MessageProcessingException("Неправильный формат даты!", exc);
                }
                return ChatState.ADD_T_ELECTRICITY.message;
            }
            case ADD_T_ELECTRICITY -> {
                try {
                    tempTariffMap.get(chat).setElectricity(Double.valueOf(text));
                    chats.updateState(chat, ChatState.ADD_T_HW);
                } catch (NumberFormatException exc) {
                    throw new MessageProcessingException("Введено некорректное число!", exc);
                }
                return ChatState.ADD_T_HW.message;
            }
            case ADD_T_HW -> {
                try {
                    tempTariffMap.get(chat).setHotWater(Double.valueOf(text));
                    chats.updateState(chat, ChatState.ADD_T_CW);
                } catch (NumberFormatException exc) {
                    throw new MessageProcessingException("Введено некорректное число!", exc);
                }
                return ChatState.ADD_T_CW.message;
            }
            case ADD_T_CW -> {
                try {
                    tempTariffMap.get(chat).setColdWater(Double.valueOf(text));
                    chats.updateState(chat, ChatState.ADD_T_DRAINAGE);
                } catch (NumberFormatException exc) {
                    throw new MessageProcessingException("Введено некорректное число!", exc);
                }
                return ChatState.ADD_T_DRAINAGE.message;
            }
            case ADD_T_DRAINAGE -> {
                try {
                    tempTariffMap.get(chat).setDrainage(Double.valueOf(text));
                    chats.updateState(chat, ChatState.MAIN);
                    dataManager.addTariff(tempTariffMap.get(chat));
                } catch (NumberFormatException exc) {
                    throw new MessageProcessingException("Введено некорректное число!", exc);
                } catch (SQLException exc) {
                    throw new MessageProcessingException("Ошибка при записи в базу данных: " + exc.getMessage(), exc);
                }
                return "Данные сохранены!";
            }
            default -> throw new MessageProcessingException("Некорректное состояние бота!");
        }
    }
}
