package chizhikov.utilitiesbot.bot.extensions;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.List;

public class KeyboardRowExt extends KeyboardRow {
    public KeyboardRowExt(List<KeyboardButton> buttons) {
        super();
        addAll(buttons);
    }

    public KeyboardRowExt(KeyboardButton button) {
        super();
        add(button);
    }
}
