from selenium import webdriver
import time
import unittest
from ddt import data,ddt,unpack

# 文章详情

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
    def test_detail(self):
        driver = self.driver
        driver.get(self.url+"/login.html")
        time.sleep(2)
        driver.find_element_by_id("username").send_keys("root")
        driver.find_element_by_id("password").send_keys("111111")
        driver.find_element_by_xpath("/html/body/div/input[3]").click()
        time.sleep(2)
        driver.find_element_by_xpath("//*[@id='tab']/tbody/tr[2]/td[2]/a").click()
        time.sleep(2)
        driver.back()
        time.sleep(2)

    def test_detail(self):
        driver = self.driver
        driver.get(self.url + "/list.html")
        time.sleep(2)
        driver.find_element_by_xpath("//*[@id='tab']/tbody/tr[2]/td[2]/a").click()
        time.sleep(2)