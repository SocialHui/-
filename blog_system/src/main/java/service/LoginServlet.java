package service;

import dao.UserInfoDao;
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

public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        int state = -1;
        String msg = "";

        if (username != null && password != null) {
            UserInfo userInfo = new UserInfo();
            userInfo.setUsername(username);
            userInfo.setPassword(password);
            boolean flag = false;
            try {
                //flag = UserInfoDao.isLogin(userInfo);
                userInfo = UserInfoDao.getUserinfo(userInfo);
                if (userInfo.getId() >= 1) {
                    //代表登录成功
                    state = 200;
                    HttpSession session = request.getSession(true);
                    session.setAttribute("userinfo",userInfo);    //在获取文章的时候要用到session信息
                } else {
                    state = 100;
                    msg = "用户名或者密码输入错误，请重新输入";
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
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
