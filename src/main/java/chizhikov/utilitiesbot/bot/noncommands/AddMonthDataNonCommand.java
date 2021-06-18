package chizhikov.utilitiesbot.bot.noncommands;

import chizhikov.utilitiesbot.bot.exceptions.MessageProcessingException;
import chizhikov.utilitiesbot.bot.userdata.ChatState;
import chizhikov.utilitiesbot.bot.userdata.Chats;
import chizhikov.utilitiesbot.data.DataManager;
import chizhikov.utilitiesbot.data.entities.MonthData;
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
public class AddMonthDataNonCommand extends AbstractNonCommand {
    private final Map<Chat, MonthData> tempMonthDataMap;

    public AddMonthDataNonCommand(Chats chats, DataManager dataManager) {
        super(chats, dataManager);
        tempMonthDataMap = new HashMap<>();
        states = Set.of(
                ChatState.ADD_MD_DATE,
                ChatState.ADD_MD_ELECTRICITY,
                ChatState.ADD_MD_HW_BATH,
                ChatState.ADD_MD_CW_BATH,
                ChatState.ADD_MD_HW_KITCHEN,
                ChatState.ADD_MD_CW_KITCHEN
        );
    }

    @Override
    public SendMessage execute(Chat chat, String text) throws MessageProcessingException {
        SendMessage message = new SendMessage();
        ChatState chatState = chats.getState(chat);
        switch (chatState) {
            case ADD_MD_DATE -> {
                try {
                    tempMonthDataMap.put(chat, new MonthData());
                    tempMonthDataMap.get(chat).setDate(LocalDate.parse(text));
                    chats.updateState(chat, ChatState.ADD_MD_ELECTRICITY);
                } catch (DateTimeParseException exc) {
                    throw new MessageProcessingException("Неправильный формат даты!", exc);
                }
                message.setText(ChatState.ADD_MD_ELECTRICITY.message);
            }
            case ADD_MD_ELECTRICITY -> {
                try {
                    tempMonthDataMap.get(chat).setElectricity(Integer.valueOf(text));
                    chats.updateState(chat, ChatState.ADD_MD_HW_BATH);
                } catch (NumberFormatException exc) {
                    throw new MessageProcessingException("Введено некорректное число!", exc);
                }
                message.setText(ChatState.ADD_MD_HW_BATH.message);
            }
            case ADD_MD_HW_BATH -> {
                try {
                    tempMonthDataMap.get(chat).setHotWaterBath(Integer.valueOf(text));
                    chats.updateState(chat, ChatState.ADD_MD_CW_BATH);
                } catch (NumberFormatException exc) {
                    throw new MessageProcessingException("Введено некорректное число!", exc);
                }
                message.setText(ChatState.ADD_MD_CW_BATH.message);
            }
            case ADD_MD_CW_BATH -> {
                try {
                    tempMonthDataMap.get(chat).setColdWaterBath(Integer.valueOf(text));
                    chats.updateState(chat, ChatState.ADD_MD_HW_KITCHEN);
                } catch (NumberFormatException exc) {
                    throw new MessageProcessingException("Введено некорректное число!", exc);
                }
                message.setText(ChatState.ADD_MD_HW_KITCHEN.message);
            }
            case ADD_MD_HW_KITCHEN -> {
                try {
                    tempMonthDataMap.get(chat).setHotWaterKitchen(Integer.valueOf(text));
                    chats.updateState(chat, ChatState.ADD_MD_CW_KITCHEN);
                } catch (NumberFormatException exc) {
                    throw new MessageProcessingException("Введено некорректное число!", exc);
                }
                message.setText(ChatState.ADD_MD_CW_KITCHEN.message);
            }
            case ADD_MD_CW_KITCHEN -> {
                try {
                    tempMonthDataMap.get(chat).setColdWaterKitchen(Integer.valueOf(text));
                    chats.updateState(chat, ChatState.MAIN);
                    dataManager.addMonthData(tempMonthDataMap.get(chat));
                } catch (NumberFormatException exc) {
                    throw new MessageProcessingException("Введено некорректное число!", exc);
                } catch (SQLException exc) {
                    throw new MessageProcessingException("Ошибка при записи в базу данных: " + exc.getMessage(), exc);
                }
                message.setText("Данные сохранены!");
            }
        }
        return message;
    }
}
