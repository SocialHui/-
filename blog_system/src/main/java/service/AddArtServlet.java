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

public class AddArtServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int state = -1;
        String msg = "";

        String title = request.getParameter("title");
        String content = request.getParameter("content");

        int uid = 0;
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("userinfo") != null) {
            UserInfo userInfo = (UserInfo) session.getAttribute("userinfo");
            uid = userInfo.getId();
            if (title != null && content != null && !title.equals("") && !content.equals("")) {
                ArticleInfoDao articleInfoDao = new ArticleInfoDao();
                try {
                    int num = articleInfoDao.add(title,content,uid);
                    if (num >= 1) {
                        state = 200;
                    } else {
                        msg = "操作数据库失败";
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            } else {
                msg = "参数不完整";
            }
        } else {
            msg = "未登录，不能操作";
        }

        HashMap<String,Object> map = new HashMap<>();
        map.put("msg",msg);
        map.put("state",state);
        ResultJSONUtils.write(response,map);
    }
}
