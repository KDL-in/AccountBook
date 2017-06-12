package dao;

import entity.Category;
import util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by KundaLin on 17/6/5.
 */
public class CategoryDAO {

    public static int InquryCid(String cname) {
        int cid = -1;
        Connection connection = DBUtil.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT cid FROM Category WHERE cname = ?");
            preparedStatement.setString(1, cname);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                cid = resultSet.getInt("cid");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cid;
    }

    public static List<Category> getList() {
        List<Category>result= new ArrayList<>();
        try {
            Connection connection = DBUtil.getConnection();
            String sql = "select * from Category";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Category newCategory = new Category();
                newCategory.cid = resultSet.getInt("cid");
                newCategory.cname = resultSet.getString("cname");
                result.add(newCategory);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void add(Category newCategory) {
        Connection connection = DBUtil.getConnection();
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("INSERT INTO Category(cname) VALUES(?)");
            preparedStatement.setString(1, newCategory.cname);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
