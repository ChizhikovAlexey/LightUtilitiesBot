package chizhikov.utilitiesbot.data.db.dao;

import chizhikov.utilitiesbot.data.db.entities.Tariff;
import lombok.AllArgsConstructor;

import java.sql.*;
import java.time.LocalDate;

@AllArgsConstructor
public class TariffDaoImpl implements TariffDao {
    private final String JDBC_URL;

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL);
    }

    private Tariff fulfillResult(ResultSet resultSet) throws SQLException {
        Tariff result = new Tariff();
        result.setColdWater(resultSet.getDouble("cold_water"));
        result.setHotWater(resultSet.getDouble("hot_water"));
        result.setElectricity(resultSet.getDouble("electricity"));
        result.setDrainage(resultSet.getDouble("drainage"));
        result.setDate(resultSet.getDate("init_date").toLocalDate());
        result.setId(resultSet.getInt("tariff_id"));
        return result;
    }

    @Override
    public Tariff getTariffById(int id) throws SQLException {
        Connection connection;
        Tariff result = null;
        connection = getConnection();
        String query = "SELECT * FROM tariffs WHERE tariff_id = %d";
        PreparedStatement statement = connection.prepareStatement(String.format(query, id));
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            result = fulfillResult(resultSet);
        }
        return result;
    }

    @Override
    public Tariff getTariffByDate(LocalDate date) throws SQLException {
        Connection connection;
        Tariff result = null;
        connection = getConnection();
        String query = "SELECT * FROM tariffs WHERE init_date <= '%s' ORDER by init_date DESC LIMIT 1";
        PreparedStatement statement = connection.prepareStatement(String.format(query, date.toString()));
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            result = fulfillResult(resultSet);
        }
        return result;
    }

    @Override
    public void addTariff(Tariff tariff) throws SQLException {
        Connection connection;
        connection = getConnection();
        String query = "INSERT INTO tariffs (electricity, hot_water, cold_water, " +
                "drainage, init_date)" +
                "VALUES(%f, %f, %f, %f, '%s')";
        PreparedStatement statement = connection.prepareStatement(String.format(
                query,
                tariff.getElectricity(),
                tariff.getHotWater(),
                tariff.getColdWater(),
                tariff.getDrainage(),
                tariff.getDate().toString()));
        statement.execute();
    }
}
