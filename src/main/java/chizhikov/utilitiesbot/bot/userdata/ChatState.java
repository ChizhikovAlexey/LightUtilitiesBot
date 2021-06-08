package chizhikov.utilitiesbot.bot.userdata;

public enum ChatState {
    NONE("Работа не начата!"),

    MAIN("Готов к работе!"),

    ADD_MD_ELECTRICITY("Электроэнергия:"),
    ADD_MD_HW_BATH("Горячая вода (санузел):"),
    ADD_MD_CW_BATH("Холодная вода (санузел)"),
    ADD_MD_HW_KITCHEN("Горячая вода (кухня)"),
    ADD_MD_CW_KITCHEN("Холодная вода (кухня)"),
    ADD_MD_DATE("Дата в формате \"гггг-мм-дд\"");

    public final String message;

    private ChatState(String message) {
        this.message = message;
    }
}
