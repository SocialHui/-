from selenium import webdriver
import time
import unittest
from ddt import data,ddt,unpack

# 文章列表展示

@ddt
class Add(unittest.TestCase):

    def setUp(self):
        self.driver = webdriver.Chrome()
        self.driver.maximize_window()
        self.url = "http://localhost:8080/blog_system_war"
        self.driver.implicitly_wait(30)

    def tearDown(self):
        self.driver.quit()

    # 文章展示，文章详情展示，文章阅读量增加
    @unittest.skip("skipping")
    def test_list(self):
        driver = self.driver
        driver.get(self.url + "/list.html")
        time.sleep(2)
        driver.find_element_by_xpath("//*[@id='tab']/tbody/tr[2]/td[2]/a").click()
        time.sleep(2)
        driver.back()
        time.sleep(2)

    # 文章在第一页点击上一页
    @unittest.skip("skipping")
    def test_list1(self):
        driver = self.driver
        driver.get(self.url + "/list.html")
        time.sleep(2)
        driver.find_element_by_link_text("上一页").click()
        time.sleep(2)

    # 一直点击下一页
    @unittest.skip("skipping")
    def test_list2(self):
        driver = self.driver
        driver.get(self.url + "/list.html")
        time.sleep(2)
        driver.find_element_by_link_text("下一页").click()
        time.sleep(2)
        driver.find_element_by_link_text("下一页").click()
        time.sleep(2)
        driver.find_element_by_link_text("下一页").click()
        time.sleep(2)
        driver.find_element_by_link_text("下一页").click()
        time.sleep(2)
        driver.find_element_by_link_text("下一页").click()
        time.sleep(2)
        driver.find_element_by_link_text("下一页").click()
        time.sleep(2)


    # 登录按钮
    def test_list3(self):
        driver = self.driver
        driver.get(self.url + "/list.html")
        time.sleep(2)
        driver.find_element_by_link_text("登录").click()
        time.sleep(2)