package service;

import dao.UserInfoDao;
import models.UserInfo;
import utils.ResultJSONUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

public class RegServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        int state = -1;   //状态  200表示接口执行成功
        String msg = "";

        if (username == null || password == null) {
            //参数不正确
            msg = "参数不正确";
        } else {
            UserInfoDao userInfoDao = new UserInfoDao();
            UserInfo userInfo = new UserInfo();
            userInfo.setUsername(username);
            userInfo.setPassword(password);
            //首先查询数据库里面有没有这个用户名，如果有，不能注册
            try {
                if (userInfoDao.query(userInfo)) {
                    msg = "此用户名已经存在";
                } else if (userInfo.getPassword().length() < 6) {
                    msg = "密码长度不符合要求";
                } else {
                    //用户名和密码都符合要求了，操作数据库进行插入操作
                    int num = userInfoDao.add(userInfo);
                    if (num >= 1) {
                        state = 200;
                    } else {
                        state = 100;  //操作数据库失败
                    }
                }
            } catch(SQLException throwables){
                throwables.printStackTrace();
            }
        }

        HashMap<String,Object> map = new HashMap<>();
        map.put("msg",msg);
        map.put("state",state);
        ResultJSONUtils.write(response,map);
    }
}
