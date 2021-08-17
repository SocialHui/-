package models.vo;

import models.ArticleInfo;

//vo对象的扩展层
//主要给前端用的
public class ArticleInfoVO extends ArticleInfo {
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
