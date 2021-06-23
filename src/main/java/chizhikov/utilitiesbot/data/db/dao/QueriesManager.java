package chizhikov.utilitiesbot.data.db.dao;

import chizhikov.utilitiesbot.data.db.entities.MonthData;
import chizhikov.utilitiesbot.data.db.entities.Tariff;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.glassfish.grizzly.utils.Pair;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

@AllArgsConstructor
@Slf4j
public class QueriesManager {
    private final String JDBC_URL;

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL);
    }

    public List<Pair<MonthData, Tariff>> getAllData() throws SQLException {
        Scanner scanner =
                new Scanner(
                        Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("getAllData.sql"))
                );
        StringBuilder queryBuilder = new StringBuilder();
        while (scanner.hasNextLine()) {
            queryBuilder.append(scanner.nextLine()).append('\n');
        }
        String query = queryBuilder.toString();
        System.out.println(query);
        List<Pair<MonthData, Tariff>> result = new ArrayList<>();
        PreparedStatement statement = getConnection().prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            MonthData tmpMonthData = new MonthData(
                    null,
                    resultSet.getInt("electricity"),
                    resultSet.getInt("hw_bath"),
                    resultSet.getInt("cw_bath"),
                    resultSet.getInt("hw_kitchen"),
                    resultSet.getInt("cw_kitchen"),
                    resultSet.getDate("date").toLocalDate()
            );
            Tariff tmpTariff = new Tariff(
                    null,
                    resultSet.getDouble("t_electricity"),
                    resultSet.getDouble("t_hw"),
                    resultSet.getDouble("t_cw"),
                    resultSet.getDouble("t_drainage"),
                    resultSet.getDate("date").toLocalDate()
            );
            result.add(new Pair<>(tmpMonthData, tmpTariff));
        }
        return result;
    }
}
