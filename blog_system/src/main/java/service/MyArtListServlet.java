package service;

import dao.ArticleInfoDao;
import models.ArticleInfo;
import models.UserInfo;
import utils.ResultJSONUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyArtListServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String msg = "";
        int state = -1;

        List<ArticleInfo> list = new ArrayList<>();
        //根据id进行查找个人的文章，所以登录的时候要将用户的id存储在session中
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userinfo") == null) {
            //说明用户没有登录
            msg = "用户未登录，没有操作权限";
            state = 100;
        } else {
            UserInfo userInfo = (UserInfo) session.getAttribute("userinfo");
            int uid = userInfo.getId();
            ArticleInfoDao articleInfoDao = new ArticleInfoDao();
            try {
                list = articleInfoDao.getMyArtList(uid);
                state = 200;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        HashMap<String ,Object> map = new HashMap<>();
        map.put("state",state);
        map.put("list",list);
        map.put("msg",msg);
        ResultJSONUtils.write(response,map);
    }
}
