from selenium import webdriver
import time
import unittest
from ddt import data,ddt,unpack

# 添加文章测试

@ddt
class Add(unittest.TestCase):

    def setUp(self):
        self.driver = webdriver.Chrome()
        self.driver.maximize_window()
        self.url = "http://localhost:8080/blog_system_war"
        self.driver.implicitly_wait(30)

    def tearDown(self):
        self.driver.quit()


    @unittest.skip("skipping")
    @data(["", "正文"], ["标题", ""], ["", ""], ["项目描述：项目的核心就是一个HTTP服务器，后端使用Servlet框架提供了对图片的增删查改能力，同时可以实现任意格式的图片上传到服务器的功能，上传图片的时候使用md5进行校验，保证上传的图片不会重复，查看图片的时候为保正","正文"])
    @unpack
    def test_add(self, title, content):
        driver = self.driver
        driver.get(self.url + "/login.html")
        driver.find_element_by_id("username").send_keys("root")
        driver.find_element_by_id("password").send_keys("111111")
        driver.find_element_by_xpath("/html/body/div/input[3]").click()
        time.sleep(2)
        driver.find_element_by_link_text("添加文章").click()
        time.sleep(2)
        driver.find_element_by_id("title").send_keys(title)
        time.sleep(2)
        driver.find_element_by_id("content").send_keys(content)
        time.sleep(2)
        driver.find_element_by_xpath("/html/body/div/input[2]").click()
        time.sleep(2)

    # 用户未登录进行添加文章的操作
    @unittest.skip("skipping")
    def test_add1(self):
        driver = self.driver
        driver.get(self.url+"/artlist.html")
        time.sleep(2)
        driver.find_element_by_link_text("添加文章").click()
        time.sleep(2)
        driver.find_element_by_id("title").send_keys("正文")
        time.sleep(2)
        driver.find_element_by_id("content").send_keys("正文")
        time.sleep(2)
        driver.find_element_by_xpath("/html/body/div/input[2]").click()
        time.sleep(2)


    # 文章添加的清空按钮的测试
    def test_addDel(self):
        driver = self.driver
        driver.get(self.url+"/addart.html")
        time.sleep(2)
        driver.find_element_by_id("title").send_keys("标题")
        time.sleep(2)
        driver.find_element_by_id("content").send_keys("正文")
        time.sleep(2)
        driver.find_element_by_xpath("/html/body/div/input[3]").click()
        time.sleep(2)
        driver.switch_to.alert.dismiss()
        time.sleep(2)
        driver.find_element_by_xpath("/html/body/div/input[3]").click()
        time.sleep(2)
        driver.switch_to.alert.accept()
        time.sleep(2)