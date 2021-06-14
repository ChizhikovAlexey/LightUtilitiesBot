package chizhikov.utilitiesbot.application;

import chizhikov.utilitiesbot.bot.NonCommandUpdateHandler;
import chizhikov.utilitiesbot.bot.TelegramBot;
import chizhikov.utilitiesbot.bot.commands.*;
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
import java.util.List;

@Configuration
@ComponentScan("chizhikov.utilitiesbot")
public class Config {
    private final String JDBC_DATABASE_URL = System.getProperty("JDBC_DATABASE_URL");
    private final String BOT_TOKEN = System.getProperty("BOT_TOKEN");
    private final String BOT_USERNAME = System.getProperty("BOT_USERNAME");

    @Bean("MonthDataDao")
    MonthDataDao monthDataDao() {
        return new MonthDataDaoImpl(JDBC_DATABASE_URL);
    }

    @Bean("TariffDao")
    TariffDao tariffDao() {
        return new TariffDaoImpl(JDBC_DATABASE_URL);
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
