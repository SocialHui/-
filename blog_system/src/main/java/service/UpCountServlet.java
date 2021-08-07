package service;

import dao.ArticleInfoDao;
import utils.ResultJSONUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

public class UpCountServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int state = -1;
        String msg = "";

        int id = Integer.parseInt(request.getParameter("id"));
        if (id >= 1) {
            ArticleInfoDao articleInfoDao = new ArticleInfoDao();
            try {
                state = articleInfoDao.upcount(id);
                if (state >= 1) {
                    state = 200;
                } else {
                    state = 100;
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else {
            msg = "文章的id有误";
        }

        HashMap<String,Object> map = new HashMap<>();
        map.put("state",state);
        map.put("msg",msg);
        ResultJSONUtils.write(response,map);
    }
}
