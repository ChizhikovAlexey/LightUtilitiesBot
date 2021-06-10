package chizhikov.utilitiesbot.bot.commands;

import chizhikov.utilitiesbot.application.DataManager;
import chizhikov.utilitiesbot.bot.userdata.Chats;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;

public abstract class AbstractCommand extends BotCommand {
    protected final Chats chats;
    protected final DataManager dataManager;

    public AbstractCommand(String commandIdentifier, String description, Chats chats, DataManager dataManager) {
        super(commandIdentifier, description);
        this.chats = chats;
        this.dataManager = dataManager;
    }

    void sendAnswer(AbsSender absSender, String chatId, String commandName, String text) {
        try {
            absSender.execute(SendMessage.builder().text(text).chatId(chatId).build());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    void sendAnswer(AbsSender absSender, Chat chat, String commandName, String text) {
        try {
            absSender.execute(SendMessage.builder().text(text).chatId(chat.getId().toString()).build());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    void sendFile(AbsSender absSender, Chat chat, String commandName, File file) {
        try {
            absSender.execute(SendDocument.builder().document(new InputFile(file)).chatId(chat.getId().toString()).build());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    void sendWrongStateAnswer(AbsSender absSender, Chat chat) {
        try {
            String text = "Выполнение предыдущей команды не закончено. Используйте команду /cancel для её отмены и повторите ввод!";
            absSender.execute(SendMessage.builder().text(text).chatId(chat.getId().toString()).build());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
