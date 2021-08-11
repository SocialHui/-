from selenium import webdriver
import time
import unittest
from ddt import data,ddt,unpack
import os

@ddt
class Test(unittest.TestCase):

    def setUp(self):
        self.driver = webdriver.Chrome()
        self.driver.maximize_window()
        self.url = "http://localhost:8080/blog_system_war"
        self.driver.implicitly_wait(30)

    def tearDown(self):
        self.driver.quit()


    def savescreenshot(self,driver,file_name):
        if not os.path.exists("./image_blog"):
            os.makedirs("./image_blog")
        now = time.strftime("%Y%m%d-%H%M%S",time.localtime(time.time()))
        driver.get_screenshot_as_file("./image_blog/"+now+"-"+file_name)
        time.sleep(2)

    # 文章个人列表的修改功能
    def test_artlist3(self):
        driver = self.driver
        driver.get(self.url + "/login.html")
        time.sleep(2)
        try:
            self.assertEqual("登录页面1",driver.title)
        except:
            self.savescreenshot(driver,"c.png")
        driver.find_element_by_id("username").send_keys("root")
        time.sleep(2)
        driver.find_element_by_id("password").send_keys("111111")
        time.sleep(2)
        driver.find_element_by_xpath("/html/body/div/input[3]").click()
        time.sleep(2)
        driver.find_element_by_xpath("//*[@id='tab']/tbody/tr[2]/td[5]/a[1]").click()
        time.sleep(2)
        driver.find_element_by_id("title").send_keys("修改过的标题")
        time.sleep(2)
        driver.find_element_by_id("content").send_keys("修改过的内容")
        time.sleep(2)
        driver.find_element_by_xpath("/html/body/div/input[3]").click()
        time.sleep(2)
        driver.switch_to.alert.dismiss()
        time.sleep(2)
        driver.find_element_by_xpath("/html/body/div/input[3]").click()
        time.sleep(2)
        driver.switch_to.alert.accept()
        time.sleep(2)
        driver.find_element_by_id("title").send_keys("修改过的标题")
        time.sleep(2)
        driver.find_element_by_id("content").send_keys("修改过的内容")
        time.sleep(2)
        driver.find_element_by_xpath("/html/body/div/input[2]").click()
        time.sleep(2)

    # 文章详情的展示的测试
    @unittest.skip("skipping")
    def test_artlist2(self):
        driver = self.driver
        driver.get(self.url + "/login.html")
        time.sleep(2)
        driver.find_element_by_id("username").send_keys("root")
        time.sleep(2)
        driver.find_element_by_id("password").send_keys("111111")
        time.sleep(2)
        driver.find_element_by_xpath("/html/body/div/input[3]").click()
        time.sleep(2)
        driver.find_element_by_xpath("//*[@id='tab']/tbody/tr[2]/td[2]/a").click()
        time.sleep(2)


    # 文章个人列表的删除的测试
    @unittest.skip("skipping")
    def test_artlist1(self):
        driver = self.driver
        driver.get(self.url + "/login.html")
        time.sleep(2)
        driver.find_element_by_id("username").send_keys("root")
        time.sleep(2)
        driver.find_element_by_id("password").send_keys("111111")
        time.sleep(2)
        driver.find_element_by_xpath("/html/body/div/input[3]").click()
        time.sleep(2)
        driver.find_element_by_xpath("//*[@id='tab']/tbody/tr[2]/td[5]/a[2]").click()
        time.sleep(2)
        driver.switch_to.alert.dismiss()
        time.sleep(2)
        driver.find_element_by_xpath("//*[@id='tab']/tbody/tr[2]/td[5]/a[2]").click()
        time.sleep(2)
        driver.switch_to.alert.accept()
        time.sleep(2)


    @unittest.skip("skipping")
    # 个人文章列表添加文章的测试
    @data(["文章1的标题","文章1的内容"],["文章2的标题","文章2的内容"],["","文章3的内容"],["文章3的标题",""])
    @unpack
    def test_artlist(self,title,content):
        driver = self.driver
        driver.get(self.url + "/login.html")
        time.sleep(2)
        driver.find_element_by_id("username").send_keys("root")
        time.sleep(2)
        driver.find_element_by_id("password").send_keys("111111")
        time.sleep(2)
        driver.find_element_by_xpath("/html/body/div/input[3]").click()
        time.sleep(2)
        driver.find_element_by_xpath("/html/body/div/a").click()
        time.sleep(2)
        driver.find_element_by_id("title").send_keys(title)
        time.sleep(2)
        driver.find_element_by_id("content").send_keys(content)
        time.sleep(2)
        driver.find_element_by_xpath("/html/body/div/input[2]").click()
        time.sleep(2)
        driver.switch_to.alert.accept()
        time.sleep(2)



    @unittest.skip("skipping")
    # 注册的自动化测试
    @data(["root","1111111","1111111"],["root","1111","1111"],["root1","1111111",""],["selenium","1111111","1111111"])
    @unpack
    def test_reg(self,name1,password1,password3):
        driver = self.driver
        driver.get(self.url+"/reg.html")
        time.sleep(2)
        driver.find_element_by_id("username").send_keys(name1)
        time.sleep(1)
        driver.find_element_by_id("password").send_keys(password1)
        time.sleep(1)
        driver.find_element_by_id("password2").send_keys(password3)
        time.sleep(1)
        driver.find_element_by_xpath("/html/body/div/input[5]").click()
        time.sleep(2)
        driver.switch_to.alert.dismiss()
        time.sleep(2)
        driver.find_element_by_xpath("/html/body/div/input[5]").click()
        time.sleep(2)
        driver.switch_to.alert.accept()
        time.sleep(2)
        driver.find_element_by_id("username").send_keys(name1)
        time.sleep(1)
        driver.find_element_by_id("password").send_keys(password1)
        time.sleep(1)
        driver.find_element_by_id("password2").send_keys(password3)
        time.sleep(1)
        driver.find_element_by_xpath("/html/body/div/input[4]").click()
        time.sleep(2)
        driver.switch_to.alert.accept()
        time.sleep(2)

    @unittest.skip("skipping")
    # 登录测试
    @data(["root1","111111222"],["root","111111"],["zazza","111111"],["root",""],["",""])
    @unpack
    def test_login(self,name,password):
        driver = self.driver
        driver.get(self.url + "/login.html")
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
        driver.find_element_by_id("username").send_keys(name)
        time.sleep(2)
        driver.find_element_by_id("password").send_keys(password)
        time.sleep(2)
        driver.find_element_by_xpath("/html/body/div/input[3]").click()
        time.sleep(2)

    @unittest.skip("skipping")
    # 登录里面的注册按钮的测试
    def test_login1(self):
        driver = self.driver
        driver.get(self.url + "/login.html")
        time.sleep(2)
        driver.find_element_by_xpath("/html/body/div/a").click()
        time.sleep(2)

    @unittest.skip("skipping")
    # 所有文章列表展示中上一页下一页的测试
    def test_list1(self):
        driver = self.driver
        driver.get(self.url + "/list.html")
        time.sleep(2)
        driver.find_element_by_xpath("/html/body/div/a[2]").click()
        time.sleep(2)
        driver.switch_to.alert.accept()
        time.sleep(2)
        driver.find_element_by_xpath("/html/body/div/a[2]").click()
        time.sleep(2)
        driver.switch_to.alert.accept()
        time.sleep(2)
        driver.find_element_by_xpath("/html/body/div/a[3]").click()
        time.sleep(2)
        driver.find_element_by_xpath("/html/body/div/a[3]").click()
        time.sleep(2)

    @unittest.skip("skipping")
    # 所有文章列表展示中文章详情的测试
    def test_list2(self):
        driver = self.driver
        driver.get(self.url + "/list.html")
        time.sleep(2)
        driver.find_element_by_xpath("//*[@id='tab']/tbody/tr[4]/td[2]/a").click()
        time.sleep(2)

    @unittest.skip("skipping")
    # 所有文章列表展示中登录功能的测试
    def test_list3(self):
        driver = self.driver
        driver.get(self.url + "/list.html")
        time.sleep(2)
        driver.find_element_by_xpath("/html/body/div/a[1]").click()
        time.sleep(2)

