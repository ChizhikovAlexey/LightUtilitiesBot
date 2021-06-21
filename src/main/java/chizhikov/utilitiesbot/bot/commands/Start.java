package chizhikov.utilitiesbot.bot.commands;

import chizhikov.utilitiesbot.bot.KeyboardResolver;
import chizhikov.utilitiesbot.bot.userdata.ChatState;
import chizhikov.utilitiesbot.bot.userdata.ChatsManager;
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
    public Start(ChatsManager chatsManager, KeyboardResolver keyboardResolver) {
        super("start", "начало работы", chatsManager);
        this.keyboardResolver = keyboardResolver;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        if (chatsManager.getState(chat) == ChatState.NOT_STARTED) {
            chatsManager.setState(chat, ChatState.MAIN);
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
