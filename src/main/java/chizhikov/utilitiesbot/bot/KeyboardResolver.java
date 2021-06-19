package chizhikov.utilitiesbot.bot;

import chizhikov.utilitiesbot.bot.extensions.KeyboardRowExt;
import chizhikov.utilitiesbot.bot.userdata.ChatState;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class KeyboardResolver {
    private final Map<ChatState, ReplyKeyboard> keyboards;

    public KeyboardResolver() {
        keyboards = new HashMap<>();

        ReplyKeyboardMarkup mainKeyboard = new ReplyKeyboardMarkup();
        keyboards.put(ChatState.MAIN, mainKeyboard);
        mainKeyboard.setKeyboard(
                List.of(
                        new KeyboardRowExt(
                                new KeyboardButton("Получить данные")
                        )
                )
        );

        InlineKeyboardMarkup mainInsertKeyboard = new InlineKeyboardMarkup();
        keyboards.put(ChatState.MAIN_INSERT, mainInsertKeyboard);
        mainKeyboard.setKeyboard(
                List.of(
                        new KeyboardRowExt(
                                new KeyboardButton("Актуальные данные за месяц")
                        )
                )
        );
    }

    public ReplyKeyboard getKeyboard (ChatState chatState) {
        return keyboards.get(chatState);
    }
}
