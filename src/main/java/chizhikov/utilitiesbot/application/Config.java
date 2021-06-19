package chizhikov.utilitiesbot.application;

import chizhikov.utilitiesbot.bot.KeyboardResolver;
import chizhikov.utilitiesbot.bot.NonCommandUpdateHandler;
import chizhikov.utilitiesbot.bot.TelegramBot;
import chizhikov.utilitiesbot.bot.userdata.Chats;
import chizhikov.utilitiesbot.data.dao.MonthDataDao;
import chizhikov.utilitiesbot.data.dao.MonthDataDaoImpl;
import chizhikov.utilitiesbot.data.dao.TariffDao;
import chizhikov.utilitiesbot.data.dao.TariffDaoImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;

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

    @Bean("TelegramBot")
    public TelegramBot telegramBot(
            List<BotCommand> listOfCommands,
            Chats chats,
            NonCommandUpdateHandler nonCommandUpdateHandler,
            KeyboardResolver keyboardResolver) {
        return new TelegramBot(
                BOT_USERNAME,
                BOT_TOKEN,
                listOfCommands,
                chats,
                nonCommandUpdateHandler,
                keyboardResolver);
    }
}
