package chizhikov.utilitiesbot.bot.msgprocessors;

import chizhikov.utilitiesbot.bot.KeyboardResolver;
import chizhikov.utilitiesbot.bot.exceptions.MessageProcessingException;
import chizhikov.utilitiesbot.bot.extensions.MessageExtension;
import chizhikov.utilitiesbot.bot.userdata.ChatState;
import chizhikov.utilitiesbot.bot.userdata.ChatsManager;
import chizhikov.utilitiesbot.data.DataManager;
import chizhikov.utilitiesbot.data.db.export.ods.OdsExporter;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.InputFile;

import java.sql.SQLException;
import java.util.Set;

@Component
public class MainGetMessageProcessor extends AbstractMessageProcessor {
    private final OdsExporter odsExporter;

    public MainGetMessageProcessor(ChatsManager chatsManager, DataManager dataManager, KeyboardResolver keyboardResolver, OdsExporter odsExporter) {
        super(chatsManager, dataManager, keyboardResolver);
        this.odsExporter = odsExporter;
        states = Set.of(
                ChatState.MAIN_GET
        );
    }

    @Override
    public MessageExtension execute(Chat chat, String text) throws MessageProcessingException {
        MessageExtension result = new MessageExtension();
        switch (text) {
            case "Актуальные данные за месяц" -> {
                chatsManager.setState(chat, ChatState.MAIN);
                String answer;
                try {
                    answer = dataManager.getActualMonthData().toString();
                } catch (SQLException sqlException) {
                    throw new MessageProcessingException(
                            "Ошибка при обращении к базе данных:\n" + sqlException.getMessage(),
                            sqlException
                    );
                }
                result.setSendMessage(
                        SendMessage.
                                builder().
                                chatId(chat.getId().toString()).
                                replyMarkup(keyboardResolver.getKeyboard(ChatState.MAIN)).
                                text(answer).
                                build()
                );

            }
            case "Последний короткий отчёт" -> {
                chatsManager.setState(chat, ChatState.MAIN);
                String answer;
                try {
                    answer = dataManager.getActualShortRepot();
                } catch (SQLException sqlException) {
                    throw new MessageProcessingException(
                            "Ошибка при обращении к базе данных:\n" + sqlException.getMessage(),
                            sqlException
                    );
                }
                result.setSendMessage(
                        SendMessage.
                                builder().
                                chatId(chat.getId().toString()).
                                replyMarkup(keyboardResolver.getKeyboard(ChatState.MAIN)).
                                text(answer).
                                build()
                );
            }
            case "Актуальный тариф" -> {
                chatsManager.setState(chat, ChatState.MAIN);
                String answer;
                try {
                    answer = dataManager.getActualTariff().toString();
                } catch (SQLException sqlException) {
                    throw new MessageProcessingException(
                            "Ошибка при обращении к базе данных:\n" + sqlException.getMessage(),
                            sqlException
                    );
                }
                result.setSendMessage(
                        SendMessage.
                                builder().
                                chatId(chat.getId().toString()).
                                replyMarkup(keyboardResolver.getKeyboard(ChatState.MAIN)).
                                text(answer).
                                build()
                );
            }
            case "Таблица со всеми показаниями" -> {
                chatsManager.setState(chat, ChatState.MAIN);
                InputFile document;
                try {
                    document = new InputFile(odsExporter.getOds());
                } catch (SQLException sqlException) {
                    throw new MessageProcessingException(
                            "Ошибка при обращении к базе данных:\n" + sqlException.getMessage(),
                            sqlException
                    );
                }
                result.setSendDocument(
                        SendDocument.
                                builder().
                                chatId(chat.getId().toString()).
                                replyMarkup(keyboardResolver.getKeyboard(chatsManager.getState(chat))).
                                document(document).
                                build()
                );
            }
            default -> result.setSendMessage(
                    SendMessage.
                            builder().
                            chatId(chat.getId().toString()).
                            replyMarkup(keyboardResolver.getKeyboard(chatsManager.getState(chat))).
                            text("Используйте кнопки для взаимодействия с ботом!").
                            build()
            );
        }
        return result;
    }
}
