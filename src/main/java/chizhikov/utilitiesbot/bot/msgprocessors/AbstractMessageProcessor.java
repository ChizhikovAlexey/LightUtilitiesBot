package chizhikov.utilitiesbot.bot.msgprocessors;

import chizhikov.utilitiesbot.bot.KeyboardResolver;
import chizhikov.utilitiesbot.bot.exceptions.MessageProcessingException;
import chizhikov.utilitiesbot.bot.extensions.MessageExtension;
import chizhikov.utilitiesbot.bot.userdata.ChatState;
import chizhikov.utilitiesbot.bot.userdata.ChatsManager;
import chizhikov.utilitiesbot.data.DataManager;
import org.telegram.telegrambots.meta.api.objects.Chat;

import java.util.HashSet;
import java.util.Set;


public abstract class AbstractMessageProcessor {
    protected final ChatsManager chatsManager;
    protected final DataManager dataManager;
    protected final KeyboardResolver keyboardResolver;
    /**
     * Every ChatState this NonCommand handles must be stored in this Set.
     */
    protected Set<ChatState> states;

    public AbstractMessageProcessor(ChatsManager chatsManager, DataManager dataManager, KeyboardResolver keyboardResolver) {
        this.chatsManager = chatsManager;
        this.dataManager = dataManager;
        this.keyboardResolver = keyboardResolver;
        states = new HashSet<>();
    }

    /**
     * Analyze user's message and proceed an answer text.
     *
     * @param chat user's chat
     * @param text user's message text
     * @return Answer text to be sent by bot
     * @throws MessageProcessingException if an exception occurs during parsing, CRUD database-operations fail or
     *                                    if user has sent an invalid message.
     */
    public abstract MessageExtension execute(Chat chat, String text) throws MessageProcessingException;

    /***
     * @return Set of ChatStates this NonCommand Handles
     */
    public Set<ChatState> getStates() {
        return states;
    }
}
