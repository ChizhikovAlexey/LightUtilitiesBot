package chizhikov.utilitiesbot.data.web;

import chizhikov.utilitiesbot.data.db.entities.MonthData;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class VanillaWebDataManager implements WebDataManager {
    private final String login;
    private final String password;
    private final String URL;

    @Override
    public void sendMonthData(MonthData monthData) {

    }
}
