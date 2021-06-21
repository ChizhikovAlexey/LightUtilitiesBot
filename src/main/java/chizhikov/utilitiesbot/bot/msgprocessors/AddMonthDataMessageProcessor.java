package chizhikov.utilitiesbot.bot.msgprocessors;

import chizhikov.utilitiesbot.bot.KeyboardResolver;
import chizhikov.utilitiesbot.bot.exceptions.MessageProcessingException;
import chizhikov.utilitiesbot.bot.extensions.MessageExtension;
import chizhikov.utilitiesbot.bot.userdata.ChatState;
import chizhikov.utilitiesbot.bot.userdata.ChatsManager;
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
public class AddMonthDataMessageProcessor extends AbstractMessageProcessor {
    private final Map<Chat, MonthData> tempMonthDataMap;

    public AddMonthDataMessageProcessor(ChatsManager chatsManager, DataManager dataManager, KeyboardResolver keyboardResolver) {
        super(chatsManager, dataManager, keyboardResolver);
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
    public MessageExtension execute(Chat chat, String text) throws MessageProcessingException {
        MessageExtension result = new MessageExtension();
        ChatState chatState = chatsManager.getState(chat);
        switch (chatState) {
            case ADD_MD_DATE -> {
                try {
                    tempMonthDataMap.put(chat, new MonthData());
                    if (text.equals("Сегодня")) {
                        tempMonthDataMap.get(chat).setDate(LocalDate.now());
                    } else {
                        tempMonthDataMap.get(chat).setDate(LocalDate.parse(text));
                    }
                    chatsManager.setState(chat, ChatState.ADD_MD_ELECTRICITY);
                } catch (DateTimeParseException exc) {
                    throw new MessageProcessingException("Неправильный формат даты!", exc);
                }
                result.setSendMessage(
                        SendMessage.
                                builder().
                                chatId(chat.getId().toString()).
                                text(ChatState.ADD_MD_ELECTRICITY.message).
                                build()
                );
            }
            case ADD_MD_ELECTRICITY -> {
                try {
                    tempMonthDataMap.get(chat).setElectricity(Integer.valueOf(text));
                    chatsManager.setState(chat, ChatState.ADD_MD_HW_BATH);
                } catch (NumberFormatException exc) {
                    throw new MessageProcessingException("Введено некорректное число!", exc);
                }
                result.setSendMessage(
                        SendMessage.
                                builder().
                                chatId(chat.getId().toString()).
                                text(ChatState.ADD_MD_HW_BATH.message).
                                build()
                );
            }
            case ADD_MD_HW_BATH -> {
                try {
                    tempMonthDataMap.get(chat).setHotWaterBath(Integer.valueOf(text));
                    chatsManager.setState(chat, ChatState.ADD_MD_CW_BATH);
                } catch (NumberFormatException exc) {
                    throw new MessageProcessingException("Введено некорректное число!", exc);
                }
                result.setSendMessage(
                        SendMessage.
                                builder().
                                chatId(chat.getId().toString()).
                                text(ChatState.ADD_MD_CW_BATH.message).
                                build()
                );
            }
            case ADD_MD_CW_BATH -> {
                try {
                    tempMonthDataMap.get(chat).setColdWaterBath(Integer.valueOf(text));
                    chatsManager.setState(chat, ChatState.ADD_MD_HW_KITCHEN);
                } catch (NumberFormatException exc) {
                    throw new MessageProcessingException("Введено некорректное число!", exc);
                }
                result.setSendMessage(
                        SendMessage.
                                builder().
                                chatId(chat.getId().toString()).
                                text(ChatState.ADD_MD_HW_KITCHEN.message).
                                build()
                );
            }
            case ADD_MD_HW_KITCHEN -> {
                try {
                    tempMonthDataMap.get(chat).setHotWaterKitchen(Integer.valueOf(text));
                    chatsManager.setState(chat, ChatState.ADD_MD_CW_KITCHEN);
                } catch (NumberFormatException exc) {
                    throw new MessageProcessingException("Введено некорректное число!", exc);
                }
                result.setSendMessage(
                        SendMessage.
                                builder().
                                chatId(chat.getId().toString()).
                                text(ChatState.ADD_MD_CW_KITCHEN.message).
                                build()
                );
            }
            case ADD_MD_CW_KITCHEN -> {
                try {
                    tempMonthDataMap.get(chat).setColdWaterKitchen(Integer.valueOf(text));
                    chatsManager.setState(chat, ChatState.MAIN);
                    dataManager.addMonthData(tempMonthDataMap.get(chat));
                } catch (NumberFormatException exc) {
                    throw new MessageProcessingException("Введено некорректное число!", exc);
                } catch (SQLException exc) {
                    throw new MessageProcessingException("Ошибка при записи в базу данных: " + exc.getMessage(), exc);
                }
                result.setSendMessage(
                        SendMessage.
                                builder().
                                chatId(chat.getId().toString()).
                                replyMarkup(keyboardResolver.getKeyboard(ChatState.MAIN)).
                                text("Данные сохранены!").
                                build()
                );
            }
        }
        return result;
    }
}
