package chizhikov.utilitiesbot.bot;

import chizhikov.utilitiesbot.bot.exceptions.MessageProcessingException;
import chizhikov.utilitiesbot.bot.userdata.ChatState;
import chizhikov.utilitiesbot.bot.userdata.Chats;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.sql.SQLException;
import java.util.List;

public class TelegramBot extends TelegramLongPollingCommandBot {
    private final Chats chats;
    private final String username;
    private final String token;
    private final NonCommandUpdateHandler nonCommandHandler;

    public TelegramBot(String username, String token, List<BotCommand> listOfCommands, Chats chats, NonCommandUpdateHandler nonCommandUpdateHandler) {
        this.username = username;
        this.token = token;
        this.chats = chats;
        nonCommandHandler = nonCommandUpdateHandler;
        listOfCommands.forEach(this::register);
    }

    @Override
    public String getBotUsername() {
        return username;
    }

    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public void processNonCommandUpdate(Update update) {
        Message message = update.getMessage();
        Chat chat = message.getChat();
        ChatState chatState = chats.getState(chat);
        try {
            sendMessage(chat, nonCommandHandler.process(chat, message.getText()));
        } catch (MessageProcessingException msgExc) {
            sendMessage(chat, msgExc.getMessage());
            if (msgExc.getCause() instanceof SQLException) {
                chats.updateState(chat, ChatState.MAIN);
            } else {
                chats.updateState(chat, chatState);
            }
            sendMessage(chat, chats.getState(chat).message);
        }
    }

    public void sendMessage(Chat chat, String text) {
        try {
            execute(SendMessage.builder().text(text).chatId(chat.getId().toString()).build());
        } catch (TelegramApiException exc) {
            //TODO: сделать логгирование
            System.out.println("Error sending message to " + chat.getId().toString() + "!" + exc);
        }
    }
}
