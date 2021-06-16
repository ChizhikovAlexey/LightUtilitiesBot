package chizhikov.utilitiesbot.data.dao;

import chizhikov.utilitiesbot.data.entities.MonthData;

import java.sql.SQLException;
import java.time.LocalDate;

public interface MonthDataDao {
    MonthData getMonthDataById(int id);

    MonthData getMonthDataByDate(LocalDate date) throws SQLException;

    void addMonthData(MonthData monthData) throws SQLException;

    void deleteById (int id) throws SQLException;
}
