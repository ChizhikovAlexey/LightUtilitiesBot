package chizhikov.utilitiesbot.bot.noncommands;

import chizhikov.utilitiesbot.bot.KeyboardResolver;
import chizhikov.utilitiesbot.bot.exceptions.MessageProcessingException;
import chizhikov.utilitiesbot.bot.userdata.ChatState;
import chizhikov.utilitiesbot.bot.userdata.Chats;
import chizhikov.utilitiesbot.data.DataManager;
import chizhikov.utilitiesbot.data.entities.Tariff;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class AddTariffNonCommand extends AbstractNonCommand {
    private final Map<Chat, Tariff> tempTariffMap;

    public AddTariffNonCommand(Chats chats, DataManager dataManager, KeyboardResolver keyboardResolver) {
        super(chats, dataManager, keyboardResolver);
        tempTariffMap = new HashMap<>();
        states = Set.of(
                ChatState.ADD_T_DATE,
                ChatState.ADD_T_ELECTRICITY,
                ChatState.ADD_T_HW,
                ChatState.ADD_T_CW,
                ChatState.ADD_T_DRAINAGE
        );
    }

    @Override
    public SendMessage execute(Chat chat, String text) throws MessageProcessingException {
        SendMessage message = new SendMessage();
        message.setChatId(chat.getId().toString());
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
                message.setText(ChatState.ADD_T_ELECTRICITY.message);
            }
            case ADD_T_ELECTRICITY -> {
                try {
                    tempTariffMap.get(chat).setElectricity(Double.valueOf(text));
                    chats.updateState(chat, ChatState.ADD_T_HW);
                } catch (NumberFormatException exc) {
                    throw new MessageProcessingException("Введено некорректное число!", exc);
                }
                message.setText(ChatState.ADD_T_HW.message);
            }
            case ADD_T_HW -> {
                try {
                    tempTariffMap.get(chat).setHotWater(Double.valueOf(text));
                    chats.updateState(chat, ChatState.ADD_T_CW);
                } catch (NumberFormatException exc) {
                    throw new MessageProcessingException("Введено некорректное число!", exc);
                }
                message.setText(ChatState.ADD_T_CW.message);
            }
            case ADD_T_CW -> {
                try {
                    tempTariffMap.get(chat).setColdWater(Double.valueOf(text));
                    chats.updateState(chat, ChatState.ADD_T_DRAINAGE);
                } catch (NumberFormatException exc) {
                    throw new MessageProcessingException("Введено некорректное число!", exc);
                }
                message.setText(ChatState.ADD_T_DRAINAGE.message);
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
                message.setText("Данные сохранены!");
            }
            default -> throw new MessageProcessingException("Некорректное состояние бота!");
        }
        return message;
    }
}
