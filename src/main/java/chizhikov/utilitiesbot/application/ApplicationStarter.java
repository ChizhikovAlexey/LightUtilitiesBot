package chizhikov.utilitiesbot.application;

import chizhikov.utilitiesbot.bot.TelegramBot;
import io.github.cdimascio.dotenv.Dotenv;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Slf4j
public class ApplicationStarter {
    public static void main(String[] args) {
        System.getenv().forEach(System::setProperty);
        Dotenv.configure().ignoreIfMissing().systemProperties().load();
        GenericApplicationContext ctx = new AnnotationConfigApplicationContext(Config.class);
        TelegramBot telegramBot = (TelegramBot) ctx.getBean("TelegramBot");
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(telegramBot);
        } catch (TelegramApiException exception) {
            log.error("Bot fails to start!", exception);
        }
    }
}
