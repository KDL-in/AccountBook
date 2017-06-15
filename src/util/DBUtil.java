package util;

import dao.CategoryDAO;
import dao.RecordsDAO;
import dao.TempCategorysDAO;
import entity.Category;
import entity.Record;
import entity.TempCategory;
import frame.MFrame;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.io.*;
import java.sql.*;
import java.sql.Date;
import java.util.*;

/**
 * Created by KundaLin on 17/6/5.
 */
public class DBUtil {
    private static Connection connection;

    //    public static int budget,daysToMonthEnd,thisMonthTotalSpend,remainBudget, todayTotalSpend;
    static {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//            connection = DriverManager.getConnection("jdbc:sqlserver://127.0.0.1:1433;DatabaseName=WalletDB", "kundalin", "123");
//            connection = DriverManager.getConnection("jdbc:sqlserver://127.0.0.1:1433;DatabaseName=WalletDB", "sa", "");
            connection = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;integratedSecurity=true");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        return connection;
    }

    public static void autoIncreasing() {
        Date lastRecord = RecordsDAO.getLastAdd();
        if (lastRecord == null) return;
        Date today = DateUtil.utilToSQL(DateUtil.today());

        Long startTime = lastRecord.getTime();//遍历时间的左右边界及迭代器
        Long endTime = today.getTime();
        Long time;
        if (startTime == endTime)
            return;
        List<TempCategory> increasingRecords = TempCategorysDAO.getAutoIncreasingList();//读取自动增长
        for (TempCategory t :
                increasingRecords) {
            Record newRecord = new Record();
            newRecord.spend = t.delta;
            newRecord.cid = CategoryDAO.InquryCid(t.tcname);
            if (newRecord.cid == -1) {//类型不存在则添加类型
                Category newCategory = new Category();
                newCategory.cname = t.tcname;
                CategoryDAO.add(newCategory);
                newRecord.cid = CategoryDAO.InquryCid(t.tcname);
            }
            time = startTime + DateUtil.MILLISECOND_OF_ONE_DAY;
            while (time <= endTime) {
                newRecord.rdate = new Date(time);
                RecordsDAO.add(newRecord);
                time += DateUtil.MILLISECOND_OF_ONE_DAY;
            }
        }

    }

/*    public static void main(String[] args) {
        recovery("F:\\学校\\个人理财\\bak\\DB.bak");
//        readData();
    }*/

    public static void backup(String bakPath) {
        bakPath += "\\DB.bak";
        try {
            String sql = "BACKUP DATABASE [WalletDB]\n" +
                    "TO DISK\n" +
                    "=?\n" +
                    "WITH FORMAT,  NAME = 'FULL-SM-BAK', SKIP, NOREWIND, NOUNLOAD,  STATS = 10";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, bakPath);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void recovery(String recPath) {
        try {
            String sql = "USE master\n" +
                    "RESTORE DATABASE WalletDB FROM  \n" +
                    "DISK=? \n" +
                    "WITH  FILE = 1,  NOUNLOAD, REPLACE,  STATS = 10";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, recPath);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("use WalletDB");
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void close() {
        if(connection!=null)try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void checkDB() {
        File dbFile = new File("plugins/data/WalletDB.mdf");
        if(dbFile.exists()||!isDBNotExist())return;
        JOptionPane.showMessageDialog(null, "无法启动，请运行initDB.exe或查看README.txt");
        close();
        System.exit(0);
    }

    private static boolean isDBNotExist() {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement("select * From master.dbo.sysdatabases where name='WalletDB'");
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
}
