package chizhikov.utilitiesbot.data.dao;

import chizhikov.utilitiesbot.data.entities.MonthData;
import lombok.AllArgsConstructor;

import java.sql.*;
import java.time.LocalDate;

@AllArgsConstructor
public class MonthDataDaoImpl implements MonthDataDao {
    private final String JDBC_URL;

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL);
    }

    private MonthData fulfillResult(ResultSet resultSet) throws SQLException {
        MonthData result = new MonthData();
        result.setColdWaterBath(resultSet.getInt("cold_water_bath"));
        result.setHotWaterBath(resultSet.getInt("hot_water_bath"));
        result.setColdWaterKitchen(resultSet.getInt("cold_water_kitchen"));
        result.setHotWaterKitchen(resultSet.getInt("hot_water_kitchen"));
        result.setElectricity(resultSet.getInt("electricity"));
        result.setDate(resultSet.getDate("date").toLocalDate());
        result.setId(resultSet.getInt("data_id"));
        return result;
    }

    @Override
    public MonthData getMonthDataById(int id) {
        Connection connection;
        MonthData result = null;
        try {
            connection = getConnection();
            String query = "SELECT * FROM month_data WHERE data_id = %d";
            PreparedStatement statement = connection.prepareStatement(String.format(query, id));
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                result = fulfillResult(resultSet);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }

    @Override
    public MonthData getMonthDataByDate(LocalDate date) throws SQLException {
        Connection connection;
        MonthData result = null;
        connection = getConnection();
        String query = "SELECT * FROM month_data WHERE date <= '%s' ORDER by date DESC LIMIT 1";
        PreparedStatement statement = connection.prepareStatement(String.format(query, date.toString()));
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            result = fulfillResult(resultSet);
        }
        return result;
    }

    @Override
    public void addMonthData(MonthData monthData) throws SQLException {
        Connection connection;
        connection = getConnection();
        String query = "INSERT INTO month_data (electricity, hot_water_bath, cold_water_bath, " +
                "hot_water_kitchen, cold_water_kitchen, date)" +
                "VALUES(%d, %d, %d, %d, %d, '%s')";
        PreparedStatement statement = connection.prepareStatement(String.format(
                query,
                monthData.getElectricity(),
                monthData.getHotWaterBath(),
                monthData.getColdWaterBath(),
                monthData.getHotWaterKitchen(),
                monthData.getColdWaterKitchen(),
                monthData.getDate().toString()));
        statement.execute();
    }

    @Override
    public void deleteById(int id) throws SQLException {
        Connection connection;
        connection = getConnection();
        String query = "DELETE FROM month_data WHERE data_id = %d";
        PreparedStatement statement = connection.prepareStatement(String.format(query, id));
        statement.execute();
    }
}
