package chizhikov.utilitiesbot.bot.noncommands;

import chizhikov.utilitiesbot.application.DataManager;
import chizhikov.utilitiesbot.bot.exceptions.MessageProcessingException;
import chizhikov.utilitiesbot.bot.userdata.Chats;
import org.telegram.telegrambots.meta.api.objects.Chat;

public class MainNonCommand extends AbstractNonCommand{

    public MainNonCommand(Chats chats, DataManager dataManager) {
        super(chats, dataManager);
    }

    @Override
    public String execute(Chat chat, String text) throws MessageProcessingException {
        return "Используйте команды для взаимодействия с ботом! Например, /add_month_data";
    }
}
