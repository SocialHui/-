package org.example.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.digest.DigestUtils;
import org.example.dao.ImageDAO;
import org.example.exception.AppException;
import org.example.model.Image;
import org.example.util.Util;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 1.WebServlet 注解
 * 2.继承HttpServlet
 * 3.重写do...方法
 */

@WebServlet("/image")   //Tomcat运行的时候自动new一个下面的ImageServlet
@MultipartConfig    //文件的传输需要的注解，不写会报错
public class ImageServlet extends HttpServlet {

    public static final String IMAGE_DIR = "D://比特//image";   //自己的本地路径，用来存储图片

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("application/json");

        //构造要返回的响应体信息，也可以创建一个类
        Map<String,Object> map = new HashMap<>();

        try {
            //1.解析请求数据
            //因为前端的数据类型时multipart/form-data(文件上传，上传的文件可以是任意的类型)  所以不能用之前的接收方式来接受前端给到的数据了，只能用getPart的方式接收前端的数据
            Part p = req.getPart("uploadImage");   //前端的name
            //p.write("D://tupianServer");   //保存文件到服务端的路径
            Long size = p.getSize();     //获得上传文件的大小
            String contentType = p.getContentType();    //不是数据头信息里面的type，而是获取每个part（键值对）的数据格式（在body里面显示的type）
            String name = p.getSubmittedFileName();    //获取上传文件的名字


            //图片上传时间，数据库保存的是字符串，所以用日期格式化来转换
            Date date = new Date();   // java.util底下的包
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  //日期格式化，括号里面代表日期的格式
            String uploadTime = df.format(date);

            //md5的获得时通过part的输入流
            InputStream is = p.getInputStream();     //获取上传文件的输入流，就是数据
            String md5 = DigestUtils.md5Hex(is);     //根据输入流转md5

            //如果已上传该图片（md5相同），就不能插入数据和保存本地
            int num = ImageDAO.queryCount(md5);
            if (num >= 1) {
                //代表数据库里面有这张图片，不能插入图片了
                //throw new RuntimeException("上传图片重复！");

                throw new AppException("上传图片重复");    //抛出自定义异常
            }

            //path 保存为相对路径
            p.write(IMAGE_DIR+"/"+md5);  //上传的图片名可能重复，但是md5是唯一的，这样写的图片的名字就不会重复


            //代码走到这代表这张图片在数据库里面不存在
            Image image = new Image();
            image.setMd5(md5);
            image.setSize(size);
            image.setContentType(contentType);
            image.setImageName(name);
            image.setUploadTime(uploadTime);
            image.setPath("/"+md5);
            int n = ImageDAO.insert(image);
            map.put("ok",true);
            /*if (n <= 0) {
                //代表图片插入服务器失败
                throw new RuntimeException();
            }*/

        }catch (Exception e) {
            e.printStackTrace();
            //resp.setStatus(500);   //只要上面出错，不论是什么错误，都返回500

            map.put("ok",false);
            if (e instanceof AppException) {
                map.put("msg", e.getMessage());
            } else {
                map.put("msg","未知错误，请联系管理员");
            }

        }

        // ok(resp);

        String s = Util.serialize(map);
        resp.getWriter().println(s);
        //3.返回响应数据

        /*ObjectMapper mapper = new ObjectMapper();
        HashMap<String,Object> map = new HashMap();
        map.put("size",size/1024);    //  /1024之后就是KB
        map.put("contentType",contentType);
        map.put("name",name);
        resp.getWriter().println(mapper.writeValueAsString(map));    //转换成json字符串返回回去*/
    }


    //展示图片
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("application/json");

        String id = req.getParameter("imageId");
        Object o = null;
        if (id == null) {
            //代表查询所有的图片   o = List<Image>
            o = ImageDAO.queryAll();
        } else {
            //查询指定id的图片  o = image对象
            o = ImageDAO.queryOne(Integer.parseInt(id));
        }
        String json = Util.serialize(o);
        resp.getWriter().println(json);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("application/json");

        Map<String,Object> map = new HashMap<>();

        try {
            String id = req.getParameter("imageId");
            //数据库和本地文件都要删除，数据库根据id删除图片数据，

            Image image = ImageDAO.queryOne(Integer.parseInt(id));
            if (image == null) {
                //代表数据库里面没有这张图片删除失败
                throw new AppException("删除图片失败，数据库没有这张图片，请刷新重试");
            }
            int num = ImageDAO.delete(Integer.parseInt(id));
            //本地硬盘删除图片文件
            String path = IMAGE_DIR + image.getPath();
            File f = new File(path);
            f.delete();  //删除操作   删除不掉可能有两个原因1.前面用到了文件的操作，操作结束之后没有进行关闭文件  2.可能是因为文件在C盘，没有操作的权限
            //ok(resp);
            map.put("ok",true);

        } catch (Exception e) {

            map.put("ok",false);
            if (e instanceof AppException) {
                map.put("msg",e.getMessage());
            } else {
                map.put("msg","未知错误");
            }
        }

        String s = Util.serialize(map);
        resp.getWriter().println(s);
    }

    public static void ok(HttpServletResponse resp) throws IOException {
        resp.getWriter().println("{\"ok\":true}");
    }
}
