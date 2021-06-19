package chizhikov.utilitiesbot.bot.extensions;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@NoArgsConstructor
@Getter
@Setter
public class MessageExtension {
    private SendMessage sendMessage;
    private SendDocument sendDocument;
}
