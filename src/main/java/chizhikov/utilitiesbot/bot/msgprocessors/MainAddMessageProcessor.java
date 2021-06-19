package chizhikov.utilitiesbot.bot.msgprocessors;

import chizhikov.utilitiesbot.bot.KeyboardResolver;
import chizhikov.utilitiesbot.bot.exceptions.MessageProcessingException;
import chizhikov.utilitiesbot.bot.userdata.ChatState;
import chizhikov.utilitiesbot.bot.userdata.Chats;
import chizhikov.utilitiesbot.data.DataManager;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;

import java.util.Set;

@Component
public class MainAddMessageProcessor extends AbstractMessageProcessor {

    public MainAddMessageProcessor(Chats chats, DataManager dataManager, KeyboardResolver keyboardResolver) {
        super(chats, dataManager, keyboardResolver);
        states = Set.of(
                ChatState.MAIN_ADD
        );
    }

    @Override
    public SendMessage execute(Chat chat, String text) throws MessageProcessingException {
        SendMessage message = new SendMessage();
        message.setChatId(chat.getId().toString());
        switch (text) {
            case "Показания счётчиков" -> {
                chats.setState(chat, ChatState.ADD_MD_DATE);
                message.setText(ChatState.ADD_MD_DATE.message);
            }
            case "Новый тариф" -> {
                chats.setState(chat, ChatState.ADD_T_DATE);
                message.setText(ChatState.ADD_T_DATE.message);
            }
            default -> {
                message.setText("Используйте кнопки для взаимодействия с ботом!");
                message.setReplyMarkup(keyboardResolver.getKeyboard(chats.getState(chat)));
            }
        }
        return message;
    }
}