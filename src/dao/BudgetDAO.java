package dao;

import util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by KundaLin on 17/6/6.
 */
public class BudgetDAO {
    public static int getBudget() {
        int budget = 0;
        Connection connection = DBUtil.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select budget from BUDGET where bid = 1");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                budget = resultSet.getInt("budget");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return budget;
    }

    public static void alter(int budget) {
        Connection connection = DBUtil.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Budget set budget = ? WHERE bid = 1");
            preparedStatement.setInt(1,budget);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
