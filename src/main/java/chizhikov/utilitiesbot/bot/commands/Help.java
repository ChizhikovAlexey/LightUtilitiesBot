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
public class Help extends AbstractCommand {
    private final KeyboardResolver keyboardResolver;

    @Autowired
    public Help(Chats chats, KeyboardResolver keyboardResolver) {
        super("help", "начало работы", chats);
        this.keyboardResolver = keyboardResolver;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        if (chats.getState(chat) == ChatState.MAIN) {
            String help =
                    """
                            Бот предназначен для ввода показаний счётчиков и генерации отчётов.
                            Используйте кнопки для работы с ботом.
                            Также доступны команды:
                            /cancel – отменяет текущую команду.\
                            Например, если вы по ошибке начали вводить новые данные счётчиков или тарифа.\
                            Также возвращает к главному меню.
                            /help – вызывает этот текст с помощью.
                            /start – начать работу с ботом, если она ещё не начата.
                            """;
            sendMessage(
                    absSender,
                    SendMessage.
                            builder().
                            text(help).
                            chatId(chat.getId().toString()).
                            replyMarkup(keyboardResolver.getKeyboard(ChatState.MAIN)).
                            build()
            );
        } else {
            sendWrongStateAnswer(absSender, chat);
        }
    }
}
