package service;

import dao.ArticleInfoDao;
import models.ArticleInfo;
import models.vo.ArticleInfoVO;
import utils.ResultJSONUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public class ListServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int state = -1;
        String msg = "";

        //没有分页的展示
        /*List<ArticleInfoVO> articleInfoList = null;
        ArticleInfoDao articleInfoDao = new ArticleInfoDao();
        try {
            articleInfoList = articleInfoDao.getList();
            if (articleInfoList != null) {
                state = 200;
            } else {
                msg = "查询数据库失败";
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }*/


        //有分页的展示

        int cpage = Integer.parseInt(request.getParameter("cpage"));
        int psize = Integer.parseInt(request.getParameter("psize"));

        List<ArticleInfoVO> articleInfoList = null;
        ArticleInfoDao articleInfoDao = new ArticleInfoDao();
        try {
            articleInfoList = articleInfoDao.getListByPage(cpage,psize);
            if (articleInfoList != null) {
                state = 200;
            } else {
                msg = "查询数据库失败";
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        HashMap<String,Object> map = new HashMap<>();
        map.put("state",state);
        map.put("msg",msg);
        map.put("list",articleInfoList);
        ResultJSONUtils.write(response,map);
    }
}
