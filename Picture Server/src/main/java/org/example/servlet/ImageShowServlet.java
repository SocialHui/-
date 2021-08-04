package org.example.servlet;


import org.example.dao.ImageDAO;
import org.example.model.Image;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.Set;

@WebServlet("/imageShow")
public class ImageShowServlet extends HttpServlet {

    //白名单链表
    private static final Set<String> whiteList = new HashSet<>();
    static {   //白名单防盗链，白名单允许访问的内容，还可以设计为白名单+黑名单的方式
        whiteList.add("http://localhost:8080/java_image_server/index.html");
        whiteList.add("http://localhost:8080/java_image_server/");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String referer = req.getHeader("Referer");
        if (!whiteList.contains(referer)) {
            //白名单不包含当前请求的referer，不允许访问，返回一个403（登录了没有身份权限）401（没有登录，没有访问权限）
            resp.setStatus(403);
            //还可以设置响应体数据
            return;   //后面的代码不能执行了
        }


        //解析请求数据   imageId
        String id = req.getParameter("imageId");

        //业务处理    根据id查找图片path字段   通过path找到本地图片文件
        Image image = ImageDAO.queryOne(Integer.parseInt(id));
        resp.setContentType(image.getContentType());   //图片是以二进制数据放在body里面，同时要制定头信息Content_type
        //本地图片的绝对路径
        String path = ImageServlet.IMAGE_DIR+image.getPath();
        //然后去本地读图片
        //io输入流读文件
        FileInputStream fis = new FileInputStream(path);
        //返回响应  服务器本地图片的二进制数据
        OutputStream os = resp.getOutputStream();    //现在是往body输出
        byte[] bytes = new byte[1024*8];   //大小设置大一点
        int len;
        while ((len = fis.read(bytes)) != -1) {
            //代表已经读完了
            os.write(bytes,0,len);    //输出
        }
        os.flush();   //刷新缓冲区   输入流和输出流是先存储在缓冲区里面的，不是马上写道你想让他写道的地方，所以需要刷新

        fis.close();
        os.close();     //这里不是放资源后面如果需要操作问价可能会失败

    }
}
