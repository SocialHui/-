package org.example.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 共用的方法
 */
public class Util {

    private static final ObjectMapper M = new ObjectMapper();   //创建比较耗时，所以弄成全局唯一的

    //数据库连接池
    private static final MysqlDataSource DS = new MysqlDataSource();
    static {
        DS.setURL("jdbc:mysql://localhost:3306/image_system");
        DS.setUser("root");
        DS.setPassword("111111");
        DS.setUseSSL(false);
        DS.setCharacterEncoding("utf-8");
    }

    /**
     * 把java对象序列化为json字符串      Servlet响应输出的body需要json字符串
     */
    public static String serialize(Object o) {
        try {
            return M.writeValueAsString(o);   //编译时异常，必须处理，把他转为运行时异常，调用这个方法的时候旧不用处理异常
        } catch (JsonProcessingException e) {
            throw new RuntimeException("系列化java对象失败"+o,e);   //出现错误的原因可能是：
        }
    }

    /**
     * 注意使用java.sql包下面的Connection
     * 数据库连接
     */
    public static Connection getConnection () {
        try {
            return DS.getConnection();
        } catch (SQLException throwables) {
            throw new RuntimeException("连接数据库失败！"+throwables);
        }
    }

    /**
     * 数据库关闭
     * @param c
     * @param s
     * @param rs
     */
    public static void close(Connection c, Statement s, ResultSet rs) {
        try {
            if (rs != null)  rs.close();
            if (s != null) s.close();
            if (c != null) c.close();
        } catch (SQLException throwables) {
            throw new RuntimeException("释放数据库资源失败！"+ throwables);
        }
    }

    //重载方式
    public static void close(Connection c, Statement s) {
        close(c,s,null);
    }

}
