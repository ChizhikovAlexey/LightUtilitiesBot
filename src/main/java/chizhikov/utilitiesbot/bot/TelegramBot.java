package chizhikov.utilitiesbot.bot;

import chizhikov.utilitiesbot.bot.exceptions.MessageProcessingException;
import chizhikov.utilitiesbot.bot.extensions.MessageExtension;
import chizhikov.utilitiesbot.bot.userdata.ChatState;
import chizhikov.utilitiesbot.bot.userdata.ChatsManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.sql.SQLException;
import java.util.List;

@Slf4j
public class TelegramBot extends TelegramLongPollingCommandBot {
    private final ChatsManager chatsManager;
    private final String username;
    private final String token;
    private final MessageHandler messageHandler;
    private final KeyboardResolver keyboardResolver;

    public TelegramBot(String username, String token, List<BotCommand> listOfCommands, ChatsManager chatsManager, MessageHandler nonCommandUpdateHandler, KeyboardResolver keyboardResolver) {
        this.username = username;
        this.token = token;
        this.chatsManager = chatsManager;
        messageHandler = nonCommandUpdateHandler;
        this.keyboardResolver = keyboardResolver;
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
        ChatState chatState = chatsManager.getState(chat);
        try {
            sendMessageExtension(messageHandler.process(chat, message.getText()));
        } catch (MessageProcessingException msgExc) {
            sendMessage(
                    SendMessage.builder().
                            chatId(chat.getId().toString()).
                            text(msgExc.getMessage()).
                            build()
            );
            if (msgExc.getCause() instanceof SQLException) {
                chatsManager.setState(chat, ChatState.MAIN);
            } else {
                chatsManager.setState(chat, chatState);
            }
            sendMessage(
                    SendMessage.builder().
                            chatId(chat.getId().toString()).
                            text(chatsManager.getState(chat).message).
                            replyMarkup(keyboardResolver.getKeyboard(chatsManager.getState(chat))).
                            build()
            );
        }
    }

    private void sendMessageExtension(MessageExtension message) {
        try {
            if (message.getSendMessage() != null) {
                execute(message.getSendMessage());
            }
        } catch (TelegramApiException exception) {
            log.error("Error sending message to " + message.getSendMessage().getChatId() + "!", exception);
        }

        try {
            if (message.getSendDocument() != null) {
                execute(message.getSendDocument());
            }
        } catch (TelegramApiException exception) {
            log.error("Error sending document to " + message.getSendDocument().getChatId() + "!", exception);
        }
    }

    private void sendMessage(SendMessage message) {
        try {
            execute(message);
        } catch (TelegramApiException exception) {
            log.error("Error sending message to " + message.getChatId() + "!", exception);
        }
    }

    @Scheduled(cron = "0 0 17 15 * ?")
    private void remind() {
        String reminding = "Не забудьте ввести показания счётчиков в этом месяце!";
        for (String id: chatsManager.getAllChatIds()) {
            sendMessage(
                    SendMessage.builder().
                            chatId(id).
                            text(reminding).
                            build()
            );
        }
    }
}
