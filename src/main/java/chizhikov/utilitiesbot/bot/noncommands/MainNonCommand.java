package chizhikov.utilitiesbot.bot.noncommands;

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
public class MainNonCommand extends AbstractNonCommand {

    public MainNonCommand(Chats chats, DataManager dataManager, KeyboardResolver keyboardResolver) {
        super(chats, dataManager, keyboardResolver);
        states = Set.of(
                ChatState.MAIN
        );
    }

    @Override
    public SendMessage execute(Chat chat, String text) throws MessageProcessingException {
        SendMessage message = new SendMessage();
        message.setChatId(chat.getId().toString());
        switch (text) {
            case "Получить данные" -> {
                message.setText("Какие данные требуются?");
                chats.setState(chat, ChatState.MAIN_GET);
                message.setReplyMarkup(keyboardResolver.getKeyboard(chats.getState(chat)));
            }
            case "Добавить новые данные" -> {
                message.setText("Что хотите добавить?");
                chats.setState(chat, ChatState.MAIN_ADD);
                message.setReplyMarkup(keyboardResolver.getKeyboard(chats.getState(chat)));
            }
            default -> {
                message.setText("Используйте кнопки для взаимодействия с ботом!");
                message.setReplyMarkup(keyboardResolver.getKeyboard(chats.getState(chat)));
            }
        }
        return message;
    }
}
