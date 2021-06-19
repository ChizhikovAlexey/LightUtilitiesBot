package chizhikov.utilitiesbot.bot;

import chizhikov.utilitiesbot.bot.extensions.KeyboardRowExt;
import chizhikov.utilitiesbot.bot.userdata.ChatState;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class KeyboardResolver {
    private final Map<ChatState, ReplyKeyboard> keyboards;

    public KeyboardResolver() {
        keyboards = new HashMap<>();

        //MAIN
        ReplyKeyboardMarkup mainKeyboard = new ReplyKeyboardMarkup();
        keyboards.put(ChatState.MAIN, mainKeyboard);
        mainKeyboard.setKeyboard(
                List.of(
                        new KeyboardRowExt(
                                new KeyboardButton("Получить данные")
                        ),
                        new KeyboardRowExt(
                                new KeyboardButton("Добавить новые данные")
                        )
                )
        );
        mainKeyboard.setOneTimeKeyboard(false);

        //MAIN_GET
        ReplyKeyboardMarkup mainGetKeyboard = new ReplyKeyboardMarkup();
        keyboards.put(ChatState.MAIN_GET, mainGetKeyboard);
        mainGetKeyboard.setKeyboard(
                List.of(
                        new KeyboardRowExt(
                                new KeyboardButton("Актуальные данные за месяц")
                        ),
                        new KeyboardRowExt(
                                new KeyboardButton("Последний короткий отчёт")
                        ),
                        new KeyboardRowExt(
                                new KeyboardButton("Актуальный тариф")
                        )
                )
        );
        mainGetKeyboard.setOneTimeKeyboard(true);

        //MAIN_ADD
        ReplyKeyboardMarkup mainAddKeyboard = new ReplyKeyboardMarkup();
        keyboards.put(ChatState.MAIN_ADD, mainAddKeyboard);
        mainAddKeyboard.setKeyboard(
                List.of(
                        new KeyboardRowExt(
                                new KeyboardButton("Показания счётчиков")
                        ),
                        new KeyboardRowExt(
                                new KeyboardButton("Новый тариф")
                        )
                )
        );
        mainAddKeyboard.setOneTimeKeyboard(true);
    }

    public ReplyKeyboard getKeyboard (ChatState chatState) {
        return keyboards.get(chatState);
    }
}
