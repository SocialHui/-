from selenium import webdriver
import time
import unittest
from ddt import data,ddt,unpack

# 修改文章测试

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
    def test_update(self):
        driver = self.driver
        driver.get(self.url + "/login.html")
        time.sleep(2)
        driver.find_element_by_id("username").send_keys("root")
        driver.find_element_by_id("password").send_keys("111111")
        driver.find_element_by_xpath("/html/body/div/input[3]").click()
        time.sleep(2)
        driver.find_element_by_xpath("//*[@id='tab']/tbody/tr[2]/td[5]/a[1]").click()
        time.sleep(2)
        driver.find_element_by_id("title").send_keys("你好")
        time.sleep(2)
        driver.find_element_by_id("content").send_keys("Java")
        time.sleep(2)
        driver.find_element_by_xpath("/html/body/div/input[3]").click()
        time.sleep(2)
        driver.switch_to.alert.dismiss()
        time.sleep(2)
        driver.find_element_by_xpath("/html/body/div/input[2]").click()
        time.sleep(2)


    # 修改文章但是没有点击退出界面是否会提示未保存
    def test_update2(self):
        driver = self.driver
        driver.get(self.url + "/login.html")
        time.sleep(2)
        driver.find_element_by_id("username").send_keys("root")
        driver.find_element_by_id("password").send_keys("111111")
        driver.find_element_by_xpath("/html/body/div/input[3]").click()
        time.sleep(2)
        driver.find_element_by_xpath("//*[@id='tab']/tbody/tr[2]/td[5]/a[1]").click()
        time.sleep(2)
        driver.find_element_by_id("title").send_keys("你好")
        time.sleep(2)
        driver.find_element_by_id("content").send_keys("Java")
        time.sleep(2)
        driver.back()
        time.sleep(2)
