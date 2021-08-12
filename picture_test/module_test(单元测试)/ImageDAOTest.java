package org.example.dao;

import org.example.model.Image;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.util.List;

//单元测试的时候，要引入Test依赖

public class ImageDAOTest {

    /**
     * 测试根据md5查找图片
     */
    @Test
    public void queryCount() {
        try {
            int num = ImageDAO.queryCount("djfneufhhdhjd");
            if (num > 0) {
                System.out.println("有包含这个md5的图片");
            } else {
                System.out.println("没有包含这个md5的图片");
            }
        } catch (Exception e) {
            System.out.println("查询出错");
        }
    }


    /**
     * 插入图片的测试
     */
    @Test
    public void insert() {
        //正常插入数据，所以下面还要插入不正常的数据进行测试
        Image image = new Image();
        image.setContentType("image/png");
        image.setImageName("2.PNG");
        image.setPath("/dabd3541f68b6e940af42cfdda9019da");
        image.setUploadTime("2021-08-03 17:11:35");
        image.setMd5("dabd3541f68b6e940af42cfdda9019da");
        image.setSize(Long.valueOf(1242));
        //assertEquals(1,ImageDAO.insert(image),"预期与结果不符");
        int num = ImageDAO.insert(image);
        if (num > 0) {
            System.out.println("插入成功");
        } else {
            System.out.println("插入失败");
        }
    }

    @Test
    public void insert1() {
        //正常插入数据，所以下面还要插入不正常的数据进行测试
        try {
            Image image = new Image();
            image.setContentType("image/png");
            image.setImageName("2.PNG");
            image.setPath("/dabd3541f68b6e940af42cfdda9019da");
            image.setUploadTime("2021-08-03 17:11:35");
            image.setMd5("dabd3541f68b6e940af42cfdda9019da");
            assertEquals(1, ImageDAO.insert(image), "预期与结果不符");
        } catch (Exception e) {
            System.out.println("路径覆盖到catch了");
        }
    }


    /**
     * 展示图片的测试
     */
    @Test
    public void queryAll() {
        List<Image> list = ImageDAO.queryAll();
        if (list != null) {
            System.out.println("数据库有图片");
        } else {
            System.out.println("数据库没有图片");
        }
        //assertNotNull(list,"结果与预期不符");
    }

    @Test
    public void queryOne() {
        //1. 正常的搜索，数据库里面有这个数据
        Image image = ImageDAO.queryOne(-1);
        if (image == null) {
            System.out.println("没有要搜索的id的图片");
        } else {
            System.out.println("找到了要搜索的id的图片");
        }
        //assertNotNull(image,"没有要搜索的id的图片");
    }

    /**
     * 删除图片的测试
     */
    @Test
    public void delete() {
        //图片不存在
        int imageId = 31;
        int num = ImageDAO.delete(imageId);
        if (num >= 1) {
            System.out.println("图片存在，删除成功");
        } else {
            System.out.println("图片不存在，删除失败");
        }
        //assertEquals(0,num,"预期与实际不符");
    }
}