package chizhikov.utilitiesbot.database.dao;

import chizhikov.utilitiesbot.database.Tariff;

import java.sql.SQLException;
import java.time.LocalDate;

public interface TariffDao {
    Tariff getTariffById(int id) throws SQLException;

    Tariff getTariffByDate(LocalDate date) throws SQLException;

    void addTariff(Tariff tariff) throws SQLException;
}
