package org.example.test;

import java.io.*;

public class FileCopy {
    public static void main(String[] args) throws IOException {
        InputStream is = new FileInputStream("D:\\Hui\\Pictures\\Saved Pictures//1.png");   //输入流
        FileOutputStream fos = new FileOutputStream("D:\\Hui\\Pictures\\Camera Roll//1.png");   //输出流，往本地一个地方输出
        byte[] bytes = new byte[1024];
        int len;
        while ((len = is.read(bytes)) != -1) {   //返回值代表读了多长,每读一下，就放在了bytes里面
            fos.write(bytes,0,len);
        }

        fos.close();
        is.close();
    }

}
