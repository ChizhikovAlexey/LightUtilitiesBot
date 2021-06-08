package chizhikov.utilitiesbot.database.dao;

import chizhikov.utilitiesbot.database.MonthData;

import java.sql.SQLException;
import java.time.LocalDate;

public interface MonthDataDao {
    MonthData getMonthDataById(int id);

    MonthData getMonthDataByDate(LocalDate date) throws SQLException;

    void addMonthData(MonthData monthData) throws SQLException;
}
