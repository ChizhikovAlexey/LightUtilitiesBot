package chizhikov.utilitiesbot.bot.msgprocessors;

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
public class NotStartedMessageProcessor extends AbstractMessageProcessor {

    public NotStartedMessageProcessor(Chats chats, DataManager dataManager, KeyboardResolver keyboardResolver) {
        super(chats, dataManager, keyboardResolver);
        states = Set.of(
                ChatState.NOT_STARTED
        );
    }

    @Override
    public SendMessage execute(Chat chat, String text) throws MessageProcessingException {
        return SendMessage.
                builder().
                chatId(chat.getId().toString()).
                text("Поздоровайтесь с ботом командой /start =)").
                build();
    }
}
