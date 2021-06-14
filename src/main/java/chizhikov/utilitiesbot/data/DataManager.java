package chizhikov.utilitiesbot.data;

import chizhikov.utilitiesbot.data.dao.MonthDataDao;
import chizhikov.utilitiesbot.data.dao.TariffDao;
import chizhikov.utilitiesbot.data.entities.MonthData;
import chizhikov.utilitiesbot.data.entities.Report;
import chizhikov.utilitiesbot.data.entities.Tariff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.time.LocalDate;

@Component("DataManager")
public class DataManager {
    private final MonthDataDao monthDataDao;
    private final TariffDao tariffDao;

    @Autowired
    public DataManager(MonthDataDao monthDataDao, TariffDao tariffDao) {
        this.monthDataDao = monthDataDao;
        this.tariffDao = tariffDao;
    }

    public void addMonthData(MonthData monthData) throws SQLException {
        monthDataDao.addMonthData(monthData);
    }

    public void addTariff(Tariff tariff) throws SQLException {
        tariffDao.addTariff(tariff);
    }

    public Tariff getActualTariff() throws SQLException {
        return tariffDao.getTariffByDate(LocalDate.now());
    }

    public MonthData getActualMonthData() throws SQLException {
        return monthDataDao.getMonthDataByDate(LocalDate.now());
    }

    public String getActualShortRepot() throws SQLException {
        Tariff tariff = getActualTariff();
        MonthData secondMonthData = getActualMonthData();
        if (secondMonthData == null || tariff == null) {
            return "Нет данных!";
        }
        MonthData firstMonthData = monthDataDao.getMonthDataByDate(secondMonthData.getDate().minusDays(1));
        if (firstMonthData == null) {
            firstMonthData = new MonthData();
            firstMonthData.setDate(secondMonthData.getDate().minusDays(1));
            firstMonthData.setElectricity(0);
            firstMonthData.setHotWaterBath(0);
            firstMonthData.setColdWaterBath(0);
            firstMonthData.setHotWaterKitchen(0);
            firstMonthData.setColdWaterKitchen(0);
        }
        Report report = new Report(firstMonthData, secondMonthData, tariff);
        return report.getShortReport();
    }
}

