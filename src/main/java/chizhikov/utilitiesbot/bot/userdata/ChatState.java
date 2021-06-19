package chizhikov.utilitiesbot.bot.userdata;

public enum ChatState {
    NOT_STARTED("Работа не начата!"),

    MAIN("Бот готов к работе!"),
    MAIN_GET("Получение данных"),
    MAIN_ADD("Добавление данных"),

    ADD_MD_ELECTRICITY("Электроэнергия:"),
    ADD_MD_HW_BATH("Горячая вода (санузел):"),
    ADD_MD_CW_BATH("Холодная вода (санузел):"),
    ADD_MD_HW_KITCHEN("Горячая вода (кухня):"),
    ADD_MD_CW_KITCHEN("Холодная вода (кухня):"),
    ADD_MD_DATE("Дата в формате \"гггг-мм-дд\":"),

    ADD_T_ELECTRICITY("Электроэнергия:"),
    ADD_T_HW("Горячая вода:"),
    ADD_T_CW("Холодная вода:"),
    ADD_T_DRAINAGE("Водоотведение:"),
    ADD_T_DATE("Дата начала действия тарифа:");

    public final String message;

    ChatState(String message) {
        this.message = message;
    }
}
