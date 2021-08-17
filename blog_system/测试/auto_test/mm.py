from selenium import webdriver
import time
import unittest
from ddt import data,ddt,unpack
import os

# 登录功能测试

@ddt
class Login(unittest.TestCase):

    def setUp(self):
        self.driver = webdriver.Chrome()
        self.driver.maximize_window()
        self.url = "http://localhost:8080/blog_system_war"
        self.driver.implicitly_wait(30)

    def tearDown(self):
        self.driver.quit()

    # 登录测试
    @unittest.skip("skipping")
    @data(["root11","111111222"],["root","111111"],["","111111"],["root",""],["",""],["wuetydg","111111"],["root","q1q1q1"])
    @unpack
    def test_login(self,name,password):
        driver = self.driver
        driver.get(self.url + "/login.html")
        time.sleep(2)
        driver.find_element_by_id("username").send_keys(name)
        time.sleep(2)
        driver.find_element_by_id("password").send_keys(password)
        time.sleep(2)
        driver.find_element_by_xpath("/html/body/div/input[3]").click()
        time.sleep(2)

    # 登陆界面的删除按钮的操作
    @unittest.skip("skipping")
    def test_loginReg(self):
        driver = self.driver
        driver.get(self.url+"/login.html")
        time.sleep(2)
        driver.find_element_by_link_text("注册").click()
        time.sleep(2)

    # 登录界面的删除操作
    @data(["root","111111"],["","111111"],["root",""],["",""])
    @unpack
    def test_loginDel(self,name,password):
        driver = self.driver
        driver.get(self.url+"/login.html")
        time.sleep(2)
        driver.find_element_by_id("username").send_keys(name)
        time.sleep(2)
        driver.find_element_by_id("password").send_keys(password)
        time.sleep(2)
        driver.find_element_by_xpath("/html/body/div/input[4]").click()
        time.sleep(2)
        driver.switch_to.alert.dismiss()
        time.sleep(2)
        driver.find_element_by_xpath("/html/body/div/input[4]").click()
        time.sleep(2)
        driver.switch_to.alert.accept()
        time.sleep(2)


