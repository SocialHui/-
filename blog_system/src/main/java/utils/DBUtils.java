package utils;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import com.sun.xml.internal.ws.api.ha.StickyFeature;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

/**
 * 通用数据库操作类
 * 1.对外提供connect 对象
 * 2.提供统一的数据库的关闭方法
 */
public class DBUtils {

    private static MysqlDataSource dataSource = null;

    /**
     * 对外提供统一的连接对象
     * @return
     * @throws SQLException
     */
    public static Connection getConnect() throws SQLException {
        if (dataSource == null) {
            //说明是首次调用，先初始化
            dataSource = new MysqlDataSource();
            dataSource.setURL("jdbc:mysql://127.0.0.1:3306/blog_system?characterEncoding=utf-8&useSSL=true");  //设置连接的服务器的地址

            dataSource.setUser("root");
            dataSource.setPassword("111111");

        }
        return dataSource.getConnection();
    }

    public static void close (Connection connection, Statement statement, ResultSet resultSet) throws SQLException {
        if (resultSet != null) connection.close();
        if (statement != null) statement.close();
        if (connection != null) connection.close();
    }
}
