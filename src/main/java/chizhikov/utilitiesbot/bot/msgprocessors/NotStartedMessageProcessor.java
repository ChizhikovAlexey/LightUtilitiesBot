package chizhikov.utilitiesbot.bot.msgprocessors;

import chizhikov.utilitiesbot.bot.KeyboardResolver;
import chizhikov.utilitiesbot.bot.exceptions.MessageProcessingException;
import chizhikov.utilitiesbot.bot.extensions.MessageExtension;
import chizhikov.utilitiesbot.bot.userdata.ChatState;
import chizhikov.utilitiesbot.bot.userdata.ChatsManager;
import chizhikov.utilitiesbot.data.DataManager;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;

import java.util.Set;

@Component
public class NotStartedMessageProcessor extends AbstractMessageProcessor {

    public NotStartedMessageProcessor(ChatsManager chatsManager, DataManager dataManager, KeyboardResolver keyboardResolver) {
        super(chatsManager, dataManager, keyboardResolver);
        states = Set.of(
                ChatState.NOT_STARTED
        );
    }

    @Override
    public MessageExtension execute(Chat chat, String text) throws MessageProcessingException {
        MessageExtension result = new MessageExtension();
        result.setSendMessage(SendMessage.
                builder().
                chatId(chat.getId().toString()).
                text("Поздоровайтесь с ботом командой /start =)").
                build()
        );
        return result;
    }
}
