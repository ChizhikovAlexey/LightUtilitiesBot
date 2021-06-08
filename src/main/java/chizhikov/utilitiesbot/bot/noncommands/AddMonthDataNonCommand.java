package chizhikov.utilitiesbot.bot.noncommands;

import chizhikov.utilitiesbot.application.DataManager;
import chizhikov.utilitiesbot.bot.exceptions.MessageProcessingException;
import chizhikov.utilitiesbot.bot.userdata.ChatState;
import chizhikov.utilitiesbot.bot.userdata.Chats;
import chizhikov.utilitiesbot.database.MonthData;
import org.telegram.telegrambots.meta.api.objects.Chat;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

public class AddMonthDataNonCommand extends AbstractNonCommand{
    private final Map<Chat, MonthData> chatMonthDataMap;

    public AddMonthDataNonCommand(Chats chats, DataManager dataManager) {
        super(chats, dataManager);
        chatMonthDataMap = new HashMap<>();
    }

    @Override
    public String execute(Chat chat, String text) throws MessageProcessingException {
        ChatState chatState = chats.getState(chat);
        switch (chatState) {
            case ADD_MD_DATE -> {
                try {
                    chatMonthDataMap.put(chat, new MonthData());
                    chatMonthDataMap.get(chat).setDate(LocalDate.parse(text));
                    chats.updateState(chat, ChatState.ADD_MD_ELECTRICITY);
                } catch (DateTimeParseException exc) {
                    throw new MessageProcessingException("Неправильный формат даты!", exc);
                }
                return ChatState.ADD_MD_ELECTRICITY.message;
            }
            case ADD_MD_ELECTRICITY -> {
                try {
                    chatMonthDataMap.get(chat).setElectricity(Integer.valueOf(text));
                    chats.updateState(chat, ChatState.ADD_MD_HW_BATH);
                } catch (NumberFormatException exc) {
                    throw new MessageProcessingException("Введено некорректное число!", exc);
                }
                return ChatState.ADD_MD_HW_BATH.message;
            }
            case ADD_MD_HW_BATH -> {
                try {
                    chatMonthDataMap.get(chat).setHotWaterBath(Integer.valueOf(text));
                    chats.updateState(chat, ChatState.ADD_MD_CW_BATH);
                } catch (NumberFormatException exc) {
                    throw new MessageProcessingException("Введено некорректное число!", exc);
                }
                return ChatState.ADD_MD_CW_BATH.message;
            }
            case ADD_MD_CW_BATH -> {
                try {
                    chatMonthDataMap.get(chat).setColdWaterBath(Integer.valueOf(text));
                    chats.updateState(chat, ChatState.ADD_MD_HW_KITCHEN);
                } catch (NumberFormatException exc) {
                    throw new MessageProcessingException("Введено некорректное число!", exc);
                }
                return ChatState.ADD_MD_HW_KITCHEN.message;
            }
            case ADD_MD_HW_KITCHEN -> {
                try {
                    chatMonthDataMap.get(chat).setHotWaterKitchen(Integer.valueOf(text));
                    chats.updateState(chat, ChatState.ADD_MD_CW_KITCHEN);
                } catch (NumberFormatException exc) {
                    throw new MessageProcessingException("Введено некорректное число!", exc);
                }
                return ChatState.ADD_MD_CW_KITCHEN.message;
            }
            case ADD_MD_CW_KITCHEN -> {
                try {
                    chatMonthDataMap.get(chat).setColdWaterKitchen(Integer.valueOf(text));
                    chats.updateState(chat, ChatState.MAIN);
                    dataManager.addMonthData(chatMonthDataMap.get(chat));
                } catch (NumberFormatException exc) {
                    throw new MessageProcessingException("Введено некорректное число!", exc);
                } catch (Exception exc) {
                    throw new MessageProcessingException("Неизвестная ошибка!", exc);
                }
                return "Данные сохранены!";
            }
        }
        return null;
    }
}
