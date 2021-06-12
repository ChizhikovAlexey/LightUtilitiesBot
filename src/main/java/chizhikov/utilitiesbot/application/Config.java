package chizhikov.utilitiesbot.application;

import chizhikov.utilitiesbot.bot.NonCommandUpdateHandler;
import chizhikov.utilitiesbot.bot.TelegramBot;
import chizhikov.utilitiesbot.bot.commands.*;
import chizhikov.utilitiesbot.bot.noncommands.AbstractNonCommand;
import chizhikov.utilitiesbot.bot.noncommands.AddMonthDataNonCommand;
import chizhikov.utilitiesbot.bot.noncommands.AddTariffNonCommand;
import chizhikov.utilitiesbot.bot.noncommands.MainNonCommand;
import chizhikov.utilitiesbot.bot.userdata.ChatState;
import chizhikov.utilitiesbot.bot.userdata.Chats;
import chizhikov.utilitiesbot.data.DataManager;
import chizhikov.utilitiesbot.data.dao.MonthDataDao;
import chizhikov.utilitiesbot.data.dao.MonthDataDaoImpl;
import chizhikov.utilitiesbot.data.dao.TariffDao;
import chizhikov.utilitiesbot.data.dao.TariffDaoImpl;
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
    private final String DATABASE_URL = System.getProperty("DATABASE_URL");
    private final String BOT_TOKEN = System.getProperty("BOT_TOKEN");
    private final String BOT_USERNAME = System.getProperty("BOT_USERNAME");

    @Bean("MonthDataDao")
    MonthDataDao monthDataDao() {
        return new MonthDataDaoImpl(DATABASE_URL);
    }

    @Bean("TariffDao")
    TariffDao tariffDao() {
        return new TariffDaoImpl(DATABASE_URL);
    }

    @Bean("ListOfCommands")
    public ArrayList<BotCommand> listOfCommands(Chats chats, DataManager dataManager) {
        ArrayList<BotCommand> commands = new ArrayList<>();
        commands.add(new Start("start", "начало работы", chats, dataManager));
        commands.add(new AddMonthDataCommand("add_month_data", "добавить данные за месяц", chats, dataManager));
        commands.add(new AddTariffCommand("add_tariff", "добавить новый тариф", chats, dataManager));
        commands.add(new Cancel("cancel", "прервать обработку команды", chats, dataManager));
        commands.add(new GetActualTariffCommand("get_actual_tariff", "получить актуальный тариф", chats, dataManager));
        commands.add(new GetActualMonthDataCommand("get_actual_month_data", "получить актуальные показания", chats, dataManager));
        commands.add(new GetActualShortReport("get_short_report", "получить краткий отчёт за последний месяц", chats, dataManager));
        return commands;
    }

    @Bean("NonCommandMap")
    public Map<ChatState, AbstractNonCommand> nonCommandMap(Chats chats, DataManager dataManager) {
        HashMap<ChatState, AbstractNonCommand> nonCommandsMap = new HashMap<>();
        AddMonthDataNonCommand addMonthDataNonCommand = new AddMonthDataNonCommand(chats, dataManager);
        AddTariffNonCommand addTariffNonCommand = new AddTariffNonCommand(chats, dataManager);
        MainNonCommand mainNonCommand = new MainNonCommand(chats, dataManager);

        nonCommandsMap.put(ChatState.MAIN, mainNonCommand);

        nonCommandsMap.put(ChatState.ADD_MD_DATE, addMonthDataNonCommand);
        nonCommandsMap.put(ChatState.ADD_MD_ELECTRICITY, addMonthDataNonCommand);
        nonCommandsMap.put(ChatState.ADD_MD_HW_BATH, addMonthDataNonCommand);
        nonCommandsMap.put(ChatState.ADD_MD_CW_BATH, addMonthDataNonCommand);
        nonCommandsMap.put(ChatState.ADD_MD_HW_KITCHEN, addMonthDataNonCommand);
        nonCommandsMap.put(ChatState.ADD_MD_CW_KITCHEN, addMonthDataNonCommand);

        nonCommandsMap.put(ChatState.ADD_T_DATE, addTariffNonCommand);
        nonCommandsMap.put(ChatState.ADD_T_ELECTRICITY, addTariffNonCommand);
        nonCommandsMap.put(ChatState.ADD_T_HW, addTariffNonCommand);
        nonCommandsMap.put(ChatState.ADD_T_CW, addTariffNonCommand);
        nonCommandsMap.put(ChatState.ADD_T_DRAINAGE, addTariffNonCommand);

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
                BOT_USERNAME,
                BOT_TOKEN,
                listOfCommands,
                chats,
                nonCommandUpdateHandler);
    }
}
