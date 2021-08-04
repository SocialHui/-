package org.example.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString  //利用插件重写的get set toString  方法，用来简化代码

public class Image {
    private Integer imageId;   //图片id
    private String imageName;    //图片名字
    private Long size;    //图片大小
    private String uploadTime;    //图片的上传时间
    private String md5;     //文件的唯一校验
    private String contentType;    //文件的类型
    private String path;     //文件的路径
}
