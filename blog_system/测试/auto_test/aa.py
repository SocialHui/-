from selenium import webdriver
import time
import unittest
from ddt import data,ddt,unpack
import os

# 注册功能测试

@ddt
class Login(unittest.TestCase):

    def setUp(self):
        self.driver = webdriver.Chrome()
        self.driver.maximize_window()
        self.url = "http://localhost:8080/blog_system_war"
        self.driver.implicitly_wait(30)

    def tearDown(self):
        self.driver.quit()


    # 注册功能
    @data(["zzzzz","zzzzzzz","zzzzzzz"],["weqwq","       ","       "],["root","111111","111111"],["","",""],["wewewe","",""],["wewewe","1234567",""],["wewewe","1234567","111111"],["qwqwqw","12345","12345"],["qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq","111111","111111"])
    @unpack
    def test_reg(self,username,password,password2):
        driver = self.driver
        driver.get(self.url+"/reg.html")
        time.sleep(2)
        driver.find_element_by_id("username").send_keys(username)
        time.sleep(2)
        driver.find_element_by_id("password").send_keys(password)
        time.sleep(2)
        driver.find_element_by_id("password2").send_keys(password2)
        time.sleep(2)
        driver.find_element_by_xpath("/html/body/div/input[4]").click()
        time.sleep(2)

    @data(["zzzzz","zzzzzzz","zzzzzzz"],["weqwq","       ","       "],["","",""],["wewewe","1234567",""],)
    @unpack
    def test_regDel(self,username,password,password2):
        driver = self.driver
        driver.get(self.url+"/reg.html")
        time.sleep(2)
        driver.find_element_by_id("username").send_keys(username)
        time.sleep(2)
        driver.find_element_by_id("password").send_keys(password)
        time.sleep(2)
        driver.find_element_by_id("password2").send_keys(password2)
        time.sleep(2)
        driver.find_element_by_xpath("/html/body/div/input[5]").click()
        time.sleep(2)
        driver.switch_to.alert.dismiss()
        time.sleep(2)
        driver.find_element_by_xpath("/html/body/div/input[5]").click()
        time.sleep(2)
        driver.switch_to.alert.accept()
        time.sleep(2)

