package chizhikov.utilitiesbot.application;

import chizhikov.utilitiesbot.bot.NonCommandUpdateHandler;
import chizhikov.utilitiesbot.bot.TelegramBot;
import chizhikov.utilitiesbot.bot.commands.AddMonthDataCommand;
import chizhikov.utilitiesbot.bot.commands.Start;
import chizhikov.utilitiesbot.bot.noncommands.AbstractNonCommand;
import chizhikov.utilitiesbot.bot.noncommands.AddMonthDataNonCommand;
import chizhikov.utilitiesbot.bot.userdata.ChatState;
import chizhikov.utilitiesbot.bot.userdata.Chats;
import chizhikov.utilitiesbot.database.dao.MonthDataDao;
import chizhikov.utilitiesbot.database.dao.MonthDataDaoImpl;
import chizhikov.utilitiesbot.database.dao.TariffDao;
import chizhikov.utilitiesbot.database.dao.TariffDaoImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@ComponentScan("chizhikov.utilitiesbot")
public class Config {
    private final String JDBC_URL = System.getProperty("JDBC_URL");
    private final String BOT_TOKEN = System.getProperty("BOT_TOKEN");
    private final String BOT_NAME = System.getProperty("BOT_NAME");

    @Bean("MonthDataDao")
    MonthDataDao monthDataDao() {
        return new MonthDataDaoImpl(JDBC_URL);
    }

    @Bean("TariffDao")
    TariffDao tariffDao() {
        return new TariffDaoImpl(JDBC_URL);
    }

    @Bean("ListOfCommands")
    public ArrayList<BotCommand> listOfCommands(Chats chats) {
        ArrayList<BotCommand> commands = new ArrayList<>();
        commands.add(new Start("start", "Начало работы с ботом", chats));
        commands.add(new AddMonthDataCommand("add_month_data", "Добавить данные за месяц", chats));
        return commands;
    }

    @Bean("NonCommandMap")
    public Map<ChatState, AbstractNonCommand> nonCommandMap(Chats chats, DataManager dataManager) {
        HashMap<ChatState, AbstractNonCommand> nonCommandsMap = new HashMap<>();
        AddMonthDataNonCommand addMonthDataNonCommand = new AddMonthDataNonCommand(chats, dataManager);
        nonCommandsMap.put(ChatState.ADD_MD_DATE, addMonthDataNonCommand);
        nonCommandsMap.put(ChatState.ADD_MD_ELECTRICITY, addMonthDataNonCommand);
        nonCommandsMap.put(ChatState.ADD_MD_HW_BATH, addMonthDataNonCommand);
        nonCommandsMap.put(ChatState.ADD_MD_CW_BATH, addMonthDataNonCommand);
        nonCommandsMap.put(ChatState.ADD_MD_HW_KITCHEN, addMonthDataNonCommand);
        nonCommandsMap.put(ChatState.ADD_MD_CW_KITCHEN, addMonthDataNonCommand);
        return nonCommandsMap;
    }

    @Bean("NonCommandUpdateHandler")
    public NonCommandUpdateHandler nonCommandUpdateHandler(
            Map<ChatState, AbstractNonCommand> nonCommandMap,
            Chats chats) {
        return new NonCommandUpdateHandler(nonCommandMap, chats);
    }

    @Bean("TelegramBot")
    public TelegramBot telegramBot(
            List<BotCommand> listOfCommands,
            Chats chats,
            NonCommandUpdateHandler nonCommandUpdateHandler) {
        return new TelegramBot(
                BOT_NAME,
                BOT_TOKEN,
                listOfCommands,
                chats,
                nonCommandUpdateHandler);
    }
}
