package chizhikov.utilitiesbot.application;

import chizhikov.utilitiesbot.database.MonthData;
import chizhikov.utilitiesbot.database.Tariff;
import chizhikov.utilitiesbot.database.dao.MonthDataDao;
import chizhikov.utilitiesbot.database.dao.TariffDao;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.time.LocalDate;

@Component("DataManager")
public class DataManager {
    private final MonthDataDao monthDataDao;
    private final TariffDao tariffDao;

    public DataManager(MonthDataDao monthDataDao, TariffDao tariffDao) {
        this.monthDataDao = monthDataDao;
        this.tariffDao = tariffDao;
    }

    public void addMonthData (MonthData monthData) throws SQLException {
        monthDataDao.addMonthData(monthData);
    }

    public void addTariff (Tariff tariff) throws SQLException {
        tariffDao.addTariff(tariff);
    }

    public Tariff getActualTariff() throws SQLException {
        return tariffDao.getTariffByDate(LocalDate.now());
    }
}

