package chizhikov.utilitiesbot.bot.commands;

import chizhikov.utilitiesbot.bot.KeyboardResolver;
import chizhikov.utilitiesbot.bot.userdata.ChatState;
import chizhikov.utilitiesbot.bot.userdata.Chats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

@Component
public class Start extends AbstractCommand {
    private final KeyboardResolver keyboardResolver;

    @Autowired
    public Start(Chats chats, KeyboardResolver keyboardResolver) {
        super("start", "начало работы", chats);
        this.keyboardResolver = keyboardResolver;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        if (chats.getState(chat) == ChatState.NOT_STARTED) {
            chats.setState(chat, ChatState.MAIN);
            sendMessage(
                    absSender,
                    SendMessage.
                            builder().
                            text(ChatState.MAIN.message).
                            chatId(chat.getId().toString()).
                            replyMarkup(keyboardResolver.getKeyboard(ChatState.MAIN)).
                            build()
            );
        }
    }
}
