package chizhikov.utilitiesbot.bot.msgprocessors;

import chizhikov.utilitiesbot.bot.KeyboardResolver;
import chizhikov.utilitiesbot.bot.exceptions.MessageProcessingException;
import chizhikov.utilitiesbot.bot.userdata.ChatState;
import chizhikov.utilitiesbot.bot.userdata.Chats;
import chizhikov.utilitiesbot.data.DataManager;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;

import java.sql.SQLException;
import java.util.Set;

@Component
public class MainGetMessageProcessor extends AbstractMessageProcessor {

    public MainGetMessageProcessor(Chats chats, DataManager dataManager, KeyboardResolver keyboardResolver) {
        super(chats, dataManager, keyboardResolver);
        states = Set.of(
                ChatState.MAIN_GET
        );
    }

    @Override
    public SendMessage execute(Chat chat, String text) throws MessageProcessingException {
        SendMessage message = new SendMessage();
        message.setChatId(chat.getId().toString());
        switch (text) {
            case "Актуальные данные за месяц" -> {
                chats.setState(chat, ChatState.MAIN);
                message.setReplyMarkup(keyboardResolver.getKeyboard(ChatState.MAIN));
                try {
                    message.setText(dataManager.getActualMonthData().toString());
                } catch (SQLException sqlException) {
                    throw new MessageProcessingException(
                            "Ошибка при обращении к базе данных:\n" + sqlException.getMessage(),
                            sqlException
                    );
                }
            }
            case "Последний короткий отчёт" -> {
                chats.setState(chat, ChatState.MAIN);
                message.setReplyMarkup(keyboardResolver.getKeyboard(ChatState.MAIN));
                try {
                    message.setText(dataManager.getActualShortRepot());
                } catch (SQLException sqlException) {
                    throw new MessageProcessingException(
                            "Ошибка при обращении к базе данных:\n" + sqlException.getMessage(),
                            sqlException
                    );
                }
            }
            case "Актуальный тариф" -> {
                chats.setState(chat, ChatState.MAIN);
                message.setReplyMarkup(keyboardResolver.getKeyboard(ChatState.MAIN));
                try {
                    message.setText(dataManager.getActualTariff().toString());
                } catch (SQLException sqlException) {
                    throw new MessageProcessingException(
                            "Ошибка при обращении к базе данных:\n" + sqlException.getMessage(),
                            sqlException
                    );
                }
            }
            default -> {
                message.setText("Используйте кнопки для взаимодействия с ботом!");
                message.setReplyMarkup(keyboardResolver.getKeyboard(chats.getState(chat)));
            }
        }
        return message;
    }
}
