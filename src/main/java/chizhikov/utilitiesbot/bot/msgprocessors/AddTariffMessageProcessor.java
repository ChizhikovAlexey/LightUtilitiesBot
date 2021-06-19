package chizhikov.utilitiesbot.bot.msgprocessors;

import chizhikov.utilitiesbot.bot.KeyboardResolver;
import chizhikov.utilitiesbot.bot.exceptions.MessageProcessingException;
import chizhikov.utilitiesbot.bot.extensions.MessageExtension;
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
public class AddTariffMessageProcessor extends AbstractMessageProcessor {
    private final Map<Chat, Tariff> tempTariffMap;

    public AddTariffMessageProcessor(Chats chats, DataManager dataManager, KeyboardResolver keyboardResolver) {
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
    public MessageExtension execute(Chat chat, String text) throws MessageProcessingException {
        MessageExtension result = new MessageExtension();
        ChatState chatState = chats.getState(chat);
        switch (chatState) {
            case ADD_T_DATE -> {
                try {
                    tempTariffMap.put(chat, new Tariff());
                    tempTariffMap.get(chat).setDate(LocalDate.parse(text));
                    chats.setState(chat, ChatState.ADD_T_ELECTRICITY);
                } catch (DateTimeParseException exc) {
                    throw new MessageProcessingException("Неправильный формат даты!", exc);
                }
                result.setSendMessage(
                        SendMessage.
                                builder().
                                chatId(chat.getId().toString()).
                                text(ChatState.ADD_T_ELECTRICITY.message).
                                build()
                );
            }
            case ADD_T_ELECTRICITY -> {
                try {
                    tempTariffMap.get(chat).setElectricity(Double.valueOf(text));
                    chats.setState(chat, ChatState.ADD_T_HW);
                } catch (NumberFormatException exc) {
                    throw new MessageProcessingException("Введено некорректное число!", exc);
                }
                result.setSendMessage(
                        SendMessage.
                                builder().
                                chatId(chat.getId().toString()).
                                text(ChatState.ADD_T_HW.message).
                                build()
                );
            }
            case ADD_T_HW -> {
                try {
                    tempTariffMap.get(chat).setHotWater(Double.valueOf(text));
                    chats.setState(chat, ChatState.ADD_T_CW);
                } catch (NumberFormatException exc) {
                    throw new MessageProcessingException("Введено некорректное число!", exc);
                }
                result.setSendMessage(
                        SendMessage.
                                builder().
                                chatId(chat.getId().toString()).
                                text(ChatState.ADD_T_CW.message).
                                build()
                );
            }
            case ADD_T_CW -> {
                try {
                    tempTariffMap.get(chat).setColdWater(Double.valueOf(text));
                    chats.setState(chat, ChatState.ADD_T_DRAINAGE);
                } catch (NumberFormatException exc) {
                    throw new MessageProcessingException("Введено некорректное число!", exc);
                }
                result.setSendMessage(
                        SendMessage.
                                builder().
                                chatId(chat.getId().toString()).
                                text(ChatState.ADD_T_DRAINAGE.message).
                                build()
                );
            }
            case ADD_T_DRAINAGE -> {
                try {
                    tempTariffMap.get(chat).setDrainage(Double.valueOf(text));
                    chats.setState(chat, ChatState.MAIN);
                    dataManager.addTariff(tempTariffMap.get(chat));
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
            default -> throw new MessageProcessingException("Некорректное состояние бота!");
        }
        return result;
    }
}
