package chizhikov.utilitiesbot.data;

import chizhikov.utilitiesbot.data.db.dao.MonthDataDao;
import chizhikov.utilitiesbot.data.db.dao.QueriesManager;
import chizhikov.utilitiesbot.data.db.dao.TariffDao;
import chizhikov.utilitiesbot.data.db.entities.MonthData;
import chizhikov.utilitiesbot.data.db.entities.Report;
import chizhikov.utilitiesbot.data.db.entities.Tariff;
import org.glassfish.grizzly.utils.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Component("DataManager")
public class DataManager {
    private final MonthDataDao monthDataDao;
    private final TariffDao tariffDao;
    private final QueriesManager queriesManager;

    @Autowired
    public DataManager(MonthDataDao monthDataDao, TariffDao tariffDao, QueriesManager queriesManager) {
        this.monthDataDao = monthDataDao;
        this.tariffDao = tariffDao;
        this.queriesManager = queriesManager;
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

    public MonthData deleteActualMonthData() throws SQLException {
        MonthData actualMonthData = getActualMonthData();
        monthDataDao.deleteById(actualMonthData.getId());
        return actualMonthData;
    }

    public List<Pair<MonthData, Tariff>> getAllDataForOds() throws SQLException {
        return queriesManager.getAllData();
    }
}

