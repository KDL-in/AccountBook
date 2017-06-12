package dao;

import entity.Category;
import entity.TempCategory;
import util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by KundaLin on 17/6/9.
 */
public class TempCategorysDAO {
    public static List<TempCategory> getList() {
        List<TempCategory>result= new ArrayList<>();
        try {
            Connection connection = DBUtil.getConnection();
            String sql = "select * from TempCategorys";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                TempCategory newCategory = new TempCategory();
                newCategory.tcname = resultSet.getString("tcname");
                newCategory.delta = resultSet.getInt("delta");
                result.add(newCategory);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void truncate() {
        try {
            Connection connection = DBUtil.getConnection();
            String sql = "truncate Table TempCategorys";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void add(TempCategory newTemCategory) {
        Connection connection = DBUtil.getConnection();
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("INSERT INTO TempCategorys VALUES(?,?)");
            preparedStatement.setString(1, newTemCategory.tcname);
            preparedStatement.setInt(2, newTemCategory.delta);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<TempCategory> getAutoIncreacingList() {
        List<TempCategory>result= new ArrayList<>();
        try {
            Connection connection = DBUtil.getConnection();
            String sql = "select * from TempCategorys where delta!=0";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                TempCategory newCategory = new TempCategory();
                newCategory.tcname = resultSet.getString("tcname");
                newCategory.delta = resultSet.getInt("delta");
                result.add(newCategory);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
