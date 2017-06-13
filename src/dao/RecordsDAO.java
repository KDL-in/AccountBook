package dao;

import entity.Record;
import util.DBUtil;
import util.DateUtil;

import java.sql.*;
import java.sql.Date;
import java.util.*;

/**
 * Created by KundaLin on 17/6/5.
 */
public class RecordsDAO {

    public static void add(Record readRecord) {
        Connection connection = DBUtil.getConnection();
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("INSERT INTO Records(cid,spend,note,rdate) VALUES(?,?,?,?)");
            preparedStatement.setInt(1, readRecord.cid);
            preparedStatement.setInt(2, readRecord.spend);
            preparedStatement.setString(3, readRecord.note);
            preparedStatement.setDate(4, readRecord.rdate);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int getTodayTotalSpend() {
        int totalSpend = 0;
        List<Record> list = getTodayList();
        for (Record r :
                list) {
            totalSpend += r.spend;
        }
        return totalSpend;
    }

    public static int getThisMonthTotalSpend() {
        int totalSpend = 0;
        Connection connection = DBUtil.getConnection();
        List<Record> list = getThisMonthList();
        for (Record r : list) {
            totalSpend += r.spend;
        }
        return totalSpend;

    }

    public static List<Record> getThisMonthList() {
        return getList(DateUtil.utilToSQL(DateUtil.monthBegin()), DateUtil.utilToSQL(DateUtil.today()));
    }


    private static List<Record> getTodayList() {
        return getList(DateUtil.utilToSQL(DateUtil.today()));
    }

    private static List<Record> getList(Date left, Date right) {
        List<Record> result = new ArrayList<>();
        try {
            Connection connection = DBUtil.getConnection();
            String sql = "select * from RECORDS where rdate>= ? and rdate <= ? order by rdate";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDate(1, left);
            preparedStatement.setDate(2, right);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Record newRecord = new Record();
                newRecord.rid = resultSet.getInt("rid");
                newRecord.cid = resultSet.getInt("cid");
                newRecord.spend = resultSet.getInt("spend");
                newRecord.note = resultSet.getString("note");
                newRecord.rdate = resultSet.getDate("rdate");
                result.add(newRecord);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static List<Record> getListBetweenTheMouth(int year, int month) {
        java.util.Date left = DateUtil.monthBegin(year, month);
        java.util.Date right = DateUtil.monthEnd(year, month);
        return getList(DateUtil.utilToSQL(left),DateUtil.utilToSQL(right));
    }

    private static List<Record> getList(Date date) {
        return getList(date,date);
    }



    public static Record queryRid(int rid) {
        Record result = new Record();
        try {
            Connection connection = DBUtil.getConnection();
            String sql = "SELECT * FROM Records WHERE rid = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, rid);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                result.rid = resultSet.getInt("rid");
                result.cid = resultSet.getInt("cid");
                result.spend = resultSet.getInt("spend");
                result.note = resultSet.getString("note");
                result.rdate = resultSet.getDate("rdate");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void alter(Record newRecord) {
        try {
            Connection connection = DBUtil.getConnection();
            String sql=null;
            if (newRecord.spend != 0)
                sql = "UPDATE Records SET spend=? WHERE rid = ?";
            else if(newRecord.cid!=0)
                sql = "UPDATE Records SET cid=? WHERE rid = ?";
            else if(newRecord.note!=null)
                sql = "UPDATE Records SET note=? WHERE rid = ?";
            else return;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            if(newRecord.spend!=0)preparedStatement.setInt(1,newRecord.spend);
            else if(newRecord.cid!=0)preparedStatement.setInt(1,newRecord.cid);
            else if(newRecord.note!=null)preparedStatement.setString(1,newRecord.note);
            preparedStatement.setInt(2, newRecord.rid);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void delete(int rid) {
        try {
            Connection connection = DBUtil.getConnection();
            String sql = "Delete FROM Records WHERE rid = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,rid);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static Date getLastAdd() {//最后更新时间
        Date lastAddDate = null;
        try {
            Connection connection = DBUtil.getConnection();
            PreparedStatement preparedStatement =
                    connection.prepareStatement("select Max(rdate) from Records");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                lastAddDate = resultSet.getDate(1);
            }
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lastAddDate;
    }

    public static Date getFirstAdd() {
        Date firstAddDate = null;
        try {
            Connection connection = DBUtil.getConnection();
            PreparedStatement preparedStatement =
                    connection.prepareStatement("select Min(rdate) from Records");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                firstAddDate = resultSet.getDate(1);
            }
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return firstAddDate;

    }

    //插入测试数据
    public static void main(String[] args) {
   /*     Record record = new Record();
        for(int i = 1; i < 20; i++) {
            record.cid =4;
            record.spend=10;
            Calendar c = Calendar.getInstance();
            c.setTime(new java.util.Date());
            c.set(Calendar.YEAR, 2017);
            c.set(Calendar.MONTH,4);
            c.set(Calendar.DATE, i);
            c.set(Calendar.HOUR_OF_DAY,0);
            c.set(Calendar.MINUTE,0);
            c.set(Calendar.SECOND,0);
            c.set(Calendar.MILLISECOND,0);
            record.rdate = DateUtil.utilToSQL(c.getTime());
            add(record);
        }*/
      /*  Record r = queryRid(3);
        System.out.println(r.spend);*/
//        System.out.println(getLastAdd());
    }
}
