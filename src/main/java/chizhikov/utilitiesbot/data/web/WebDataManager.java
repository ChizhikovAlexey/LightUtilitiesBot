package chizhikov.utilitiesbot.data.web;

import chizhikov.utilitiesbot.data.db.entities.MonthData;

public interface WebDataManager {
    void sendMonthData(MonthData monthData);
}
