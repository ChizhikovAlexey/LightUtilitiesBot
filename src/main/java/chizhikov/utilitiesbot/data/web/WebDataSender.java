package chizhikov.utilitiesbot.data.web;

import chizhikov.utilitiesbot.data.db.entities.MonthData;

public interface WebDataSender {
    void sendMonthData(MonthData monthData);
}
