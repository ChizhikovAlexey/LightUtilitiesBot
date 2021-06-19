package chizhikov.utilitiesbot.bot.msgprocessors;

import chizhikov.utilitiesbot.bot.KeyboardResolver;
import chizhikov.utilitiesbot.bot.exceptions.MessageProcessingException;
import chizhikov.utilitiesbot.bot.extensions.MessageExtension;
import chizhikov.utilitiesbot.bot.userdata.ChatState;
import chizhikov.utilitiesbot.bot.userdata.Chats;
import chizhikov.utilitiesbot.data.DataManager;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;

import java.util.Set;

@Component
public class MainMessageProcessor extends AbstractMessageProcessor {

    public MainMessageProcessor(Chats chats, DataManager dataManager, KeyboardResolver keyboardResolver) {
        super(chats, dataManager, keyboardResolver);
        states = Set.of(
                ChatState.MAIN
        );
    }

    @Override
    public MessageExtension execute(Chat chat, String text) throws MessageProcessingException {
        MessageExtension result = new MessageExtension();
        switch (text) {
            case "Получить данные" -> {
                chats.setState(chat, ChatState.MAIN_GET);
                result.setSendMessage(
                        SendMessage.
                                builder().
                                chatId(chat.getId().toString()).
                                replyMarkup(keyboardResolver.getKeyboard(chats.getState(chat))).
                                text("Какие данные требуются?").
                                build()
                );
            }
            case "Добавить новые данные" -> {
                chats.setState(chat, ChatState.MAIN_ADD);
                chats.setState(chat, ChatState.MAIN_GET);
                result.setSendMessage(
                        SendMessage.
                                builder().
                                chatId(chat.getId().toString()).
                                replyMarkup(keyboardResolver.getKeyboard(chats.getState(chat))).
                                text("Что хотите добавить?").
                                build()
                );

            }
            default -> result.setSendMessage(
                    SendMessage.
                            builder().
                            chatId(chat.getId().toString()).
                            replyMarkup(keyboardResolver.getKeyboard(chats.getState(chat))).
                            text("Используйте кнопки для взаимодействия с ботом!").
                            build()
            );
        }
        return result;
    }
}
