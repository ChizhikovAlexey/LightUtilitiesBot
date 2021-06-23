package chizhikov.utilitiesbot.data.db.dao;

import chizhikov.utilitiesbot.data.db.entities.MonthData;

import java.sql.SQLException;
import java.time.LocalDate;

public interface MonthDataDao {
    MonthData getMonthDataById(int id);

    MonthData getMonthDataByDate(LocalDate date) throws SQLException;

    void addMonthData(MonthData monthData) throws SQLException;

    void deleteById(int id) throws SQLException;
}
