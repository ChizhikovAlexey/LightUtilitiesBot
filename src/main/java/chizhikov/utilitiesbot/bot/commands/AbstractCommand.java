package chizhikov.utilitiesbot.bot.commands;

import chizhikov.utilitiesbot.bot.userdata.ChatState;
import chizhikov.utilitiesbot.bot.userdata.Chats;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
public abstract class AbstractCommand extends BotCommand {
    protected final Chats chats;

    public AbstractCommand(String commandIdentifier, String description, Chats chats) {
        super(commandIdentifier, description);
        this.chats = chats;
    }

    void sendAnswer(AbsSender absSender, Chat chat, String commandName, String text) {
        try {
            absSender.execute(SendMessage.builder().text(text).chatId(chat.getId().toString()).build());
        } catch (TelegramApiException e) {
            log.error("Error occurred while sending message to " + chat.getId() + "!", e);
        }
    }

    void sendWrongStateAnswer(AbsSender absSender, Chat chat) {
        try {
            String text;
            if (chats.getState(chat) == ChatState.NOT_STARTED) {
                text = "Поздоровайтесь с ботом командой /start =)";
            } else {
                text = "Выполнение предыдущей команды не закончено. Используйте команду /cancel для её отмены и повторите ввод!";
            }
            absSender.execute(SendMessage.builder().text(text).chatId(chat.getId().toString()).build());
        } catch (TelegramApiException e) {
            log.error("Error occured while sending message to " + chat.getId() + "!", e);
        }
    }

    void sendMessage(AbsSender absSender, SendMessage message) {
        try {
            absSender.execute(message);
        } catch (TelegramApiException e) {
            log.error("Error occured while sending message to " + message.getChatId() + "!", e);
        }
    }
}
