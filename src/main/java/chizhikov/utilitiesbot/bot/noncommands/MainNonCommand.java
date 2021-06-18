package chizhikov.utilitiesbot.bot.noncommands;

import chizhikov.utilitiesbot.bot.exceptions.MessageProcessingException;
import chizhikov.utilitiesbot.bot.userdata.ChatState;
import chizhikov.utilitiesbot.bot.userdata.Chats;
import chizhikov.utilitiesbot.data.DataManager;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

import java.util.Set;

@Component
public class MainNonCommand extends AbstractNonCommand {

    public MainNonCommand(Chats chats, DataManager dataManager) {
        super(chats, dataManager);
        states = Set.of(
                ChatState.MAIN
        );
    }

    @Override
    public SendMessage execute(Chat chat, String text) throws MessageProcessingException {
        SendMessage message = new SendMessage();
        message.setText("Используйте команды для взаимодействия с ботом! Например, /add_month_data");
        return message;
    }
}
