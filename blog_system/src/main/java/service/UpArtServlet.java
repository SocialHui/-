package service;

import dao.ArticleInfoDao;
import models.UserInfo;
import utils.ResultJSONUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

public class UpArtServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int state = -1;
        String msg = "";

        String title = request.getParameter("title");
        String content = request.getParameter("content");
        int id = Integer.parseInt(request.getParameter("id"));

        if (title != null && content != null && id >0 && !title.equals("") && !content.equals("")) {
            HttpSession session = request.getSession(false);
            if (session != null && session.getAttribute("userinfo") != null) {

                ArticleInfoDao articleInfoDao = new ArticleInfoDao();
                try {
                    int num = articleInfoDao.update(title,content,id);
                    if (num >= 1) {
                        state = 200;
                    } else {
                        msg = "修改数据库失败";
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            } else {
                msg = "未登录，不能操作";
            }
        } else {
            msg = "参数不全";
        }

        HashMap<String,Object> map = new HashMap<>();
        map.put("msg",msg);
        map.put("state",state);
        ResultJSONUtils.write(response,map);
    }
}
