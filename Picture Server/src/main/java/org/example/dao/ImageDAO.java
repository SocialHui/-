package org.example.dao;

import org.example.model.Image;
import org.example.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据库操作
 */
public class ImageDAO {

    //先查找数据库有没有包含这个md5的图片
    public static int queryCount(String md5) {
        Connection connection = Util.getConnection();
        String sql = "select count(0) c from image_table where md5 = ?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        int num = 0;
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1,md5);
            resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getInt("c");

        } catch (SQLException throwables) {
            throw new RuntimeException("查询上传图片md5出错："+md5,throwables);
        } finally {
            //进行关闭操作
            Util.close(connection,statement,resultSet);
        }
    }


    //插入图片
    public static int insert(Image image){
        Connection connection = Util.getConnection();
        PreparedStatement statement = null;

        try {
            String sql = "insert into image_table values(null,?,?,?,?,?,?)";   //自增的用null
            statement = connection.prepareStatement(sql);
            statement.setString(1,image.getImageName());
            statement.setLong(2,image.getSize());
            statement.setString(3,image.getUploadTime());
            statement.setString(4,image.getMd5());
            statement.setString(5,image.getContentType());
            statement.setString(6,image.getPath());

            return statement.executeUpdate();
        } catch (SQLException throwables) {
            throw new RuntimeException("新增上传图片出错",throwables);
        } finally {
            Util.close(connection,statement);
        }
    }

    /**
     * 查询所有的图片
     * @return
     */
    public static List<Image> queryAll() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = Util.getConnection();
            String sql = "select * from image_table";
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
            List<Image> list = new ArrayList<>();
            while (resultSet.next()) {
                Image image = new Image();

                image.setImageId(resultSet.getInt("image_id"));
                image.setPath(resultSet.getString("path"));
                image.setImageName(resultSet.getString("image_name"));
                image.setContentType("content_type");
                image.setMd5(resultSet.getString("md5"));
                list.add(image);
            }
            return list;
        } catch (SQLException throwables) {
            throw new RuntimeException("查询所有图片出错",throwables);
        } finally {
            Util.close(connection,statement,resultSet);
        }
    }

    /**
     * 根据id查找图片
     * @param id
     * @return
     */
    public static Image queryOne(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = Util.getConnection();
            String sql = "select * from image_table where image_id = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1,id);
            resultSet = statement.executeQuery();
            Image image = null;
            while (resultSet.next()) {
                image = new Image();
                image.setImageId(resultSet.getInt("image_id"));
                image.setPath(resultSet.getString("path"));
                image.setUploadTime(resultSet.getString("upload_time"));
                image.setImageName(resultSet.getString("image_name"));
                image.setContentType("content_type");
                image.setSize(resultSet.getLong("size"));
                image.setMd5(resultSet.getString("md5"));
            }
            return image;
        } catch (SQLException throwables) {
            throw new RuntimeException("查询所有图片出错",throwables);
        } finally {
            Util.close(connection,statement,resultSet);
        }
    }

    public static int delete(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = Util.getConnection();
            connection.setAutoCommit(false);   //不自动提交
            String sql = "delete from image_table where image_id = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1,id);
            int n = statement.executeUpdate();
            connection.commit();   //提交，如果有出错，就会自动回滚
            return n;
        } catch (Exception throwables) {   //所有的异常都需要回滚
            try {
                connection.rollback();
            } catch (SQLException e) {
                throw new RuntimeException("删除图片回滚失败："+id,e);
            }
            throw new RuntimeException("删除数据库图片失败: "+id,throwables);
        } finally {
            Util.close(connection,statement);
        }
    }
}

















