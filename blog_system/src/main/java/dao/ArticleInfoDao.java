package dao;

import models.ArticleInfo;
import models.vo.ArticleInfoVO;
import utils.DBUtils;

import javax.servlet.http.Part;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarOutputStream;

public class ArticleInfoDao {

    //获取我的文章列表
    public List<ArticleInfo> getMyArtList(int uid) throws SQLException {
        List<ArticleInfo> list = new ArrayList<>();
        Connection connection = DBUtils.getConnect();
        String sql = "select * from articleinfo where uid = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1,uid);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            ArticleInfo articleInfo = new ArticleInfo();
            articleInfo.setId(resultSet.getInt("id"));
            articleInfo.setTitle(resultSet.getString("title"));
            articleInfo.setContent(resultSet.getString("content"));
            articleInfo.setCreateTime(resultSet.getTime("createtime"));
            articleInfo.setUpdateTime(resultSet.getTime("updatetime"));
            articleInfo.setrCount(resultSet.getInt("rcount"));
            articleInfo.setState(resultSet.getInt("state"));
            list.add(articleInfo);
        }
        return list;
    }

    //删除文章
    public int delArticleById(int id) throws SQLException {
        int result = 0;
        Connection connection = DBUtils.getConnect();
        String sql = "delete from articleinfo where id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1,id);
        result = statement.executeUpdate();
        DBUtils.close(connection,statement,null);
        return result;
    }

    //添加文章
    public int add(String title, String content, int uid) throws SQLException {
        int result = 0;
        Connection connection = DBUtils.getConnect();
        String sql = "insert into articleinfo(title,content,uid) values (?,?,?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1,title);
        statement.setString(2,content);
        statement.setInt(3,uid);
        result = statement.executeUpdate();
        DBUtils.close(connection,statement,null);
        return result;
    }

    //修改文章的操作
    public int update(String title, String content, int id) throws SQLException {
        int result = 0;
        Connection connection = DBUtils.getConnect();
        String sql = "update articleinfo set title = ? ,content = ? where id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1,title);
        statement.setString(2,content);
        statement.setInt(3,id);
        result = statement.executeUpdate();
        DBUtils.close(connection,statement,null);
        return result;
    }

    //查询文章的具体内容
    public ArticleInfoVO getArtById(int id) throws SQLException {
        ArticleInfoVO articleInfoVO = new ArticleInfoVO();
        Connection connection = DBUtils.getConnect();
        String sql = "select a.*,b.username from articleinfo a left join userinfo b on a.uid = b.id where a.id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1,id);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            articleInfoVO.setTitle(resultSet.getString("title"));
            articleInfoVO.setContent(resultSet.getString("content"));
            articleInfoVO.setCreateTime(resultSet.getDate("createtime"));
            articleInfoVO.setrCount(resultSet.getInt("rcount"));
            articleInfoVO.setUsername(resultSet.getString("username"));
        }
        DBUtils.close(connection,statement,resultSet);
        return articleInfoVO;
    }

    //获取所有人的文章列表
    public List<ArticleInfoVO> getList() throws SQLException {
        List<ArticleInfoVO> list = new ArrayList<>();
        Connection connection = DBUtils.getConnect();
        String sql = "select a.*,u.username from articleinfo a left join userinfo u on a.uid = u.id";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            ArticleInfoVO articleInfoVO = new ArticleInfoVO();
            articleInfoVO.setTitle(resultSet.getString("title"));
            articleInfoVO.setId(resultSet.getInt("id"));
            articleInfoVO.setCreateTime(resultSet.getTime("createtime"));
            articleInfoVO.setUsername(resultSet.getString("username"));
            articleInfoVO.setrCount(resultSet.getInt("rcount"));
            list.add(articleInfoVO);
        }
        DBUtils.close(connection,statement,resultSet);
        return list;
    }

    //查询分页数据
    public List<ArticleInfoVO> getListByPage(int cpage, int psize) throws SQLException {
        List<ArticleInfoVO> list = new ArrayList<>();
        Connection connection = DBUtils.getConnect();
        String sql = "select a.*,u.username from articleinfo a left join userinfo u on a.uid = u.id limit ?,?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1,(cpage-1)*psize);
        statement.setInt(2,psize);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            ArticleInfoVO articleInfoVO = new ArticleInfoVO();
            articleInfoVO.setTitle(resultSet.getString("title"));
            articleInfoVO.setId(resultSet.getInt("id"));
            articleInfoVO.setCreateTime(resultSet.getTime("createtime"));
            articleInfoVO.setUsername(resultSet.getString("username"));
            articleInfoVO.setrCount(resultSet.getInt("rcount"));
            list.add(articleInfoVO);
        }
        DBUtils.close(connection,statement,resultSet);
        return list;
    }

    //阅读量统计+1
    public int upcount(int id) throws SQLException {
        int result = 0;
        Connection connection = DBUtils.getConnect();
        String sql = "update articleinfo set rcount = rcount + 1 where id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1,id);
        result = statement.executeUpdate();
        DBUtils.close(connection,statement,null);
        return result;
    }
}
