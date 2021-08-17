package dao;

import models.UserInfo;
import utils.DBUtils;
import utils.ResultJSONUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserInfoDao {

    //用户登录
    public static boolean isLogin(UserInfo userInfo) throws SQLException {
        boolean flag = false;
        if (userInfo.getUsername() != null && userInfo.getPassword() != null) {
            Connection connection = DBUtils.getConnect();
            String sql = "select * from userinfo where username = ? and password = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,userInfo.getUsername());
            statement.setString(2,userInfo.getPassword());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                flag = true;
            }
            DBUtils.close(connection,statement,resultSet);
        }
        return flag;
    }

    public static UserInfo getUserinfo(UserInfo userInfo) throws SQLException {
        UserInfo user = new UserInfo();
        Connection connection = DBUtils.getConnect();
        String sql = "select * from userinfo where username = ? and password = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1,userInfo.getUsername());
        statement.setString(2,userInfo.getPassword());
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            user.setUsername(resultSet.getString("username"));
            user.setPassword(resultSet.getString("password"));
            user.setCreateTime(resultSet.getDate("createtime"));
            user.setId(resultSet.getInt("id"));
            user.setState(resultSet.getInt("state"));
            user.setUpdateTime(resultSet.getDate("updatetime"));
        }
        DBUtils.close(connection,statement,resultSet);
        return user;
    }

    //用户注册
    public int add(UserInfo userInfo) throws SQLException {
        int num = 0;
        if (userInfo.getUsername() != null && userInfo.getPassword() != null) {
            Connection connection = DBUtils.getConnect();
            String sql = "insert into userinfo(username,password) values (?,?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,userInfo.getUsername());
            statement.setString(2,userInfo.getPassword());
            num = statement.executeUpdate();
            DBUtils.close(connection,statement,null);
        }
        return num;
    }

    //查询数据库是存在这个用户名的用户
    public boolean query(UserInfo userInfo) throws SQLException {
        boolean flag = false;
        if (userInfo.getUsername() != null && userInfo.getPassword() != null) {
            Connection connection = DBUtils.getConnect();
            String sql = "select * from userinfo where username = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,userInfo.getUsername());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                flag = true;
            }
            DBUtils.close(connection,statement,resultSet);
        }
        return flag;
    }

    //获取userinfo对象

}
