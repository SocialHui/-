from ddt import data, ddt
from selenium import webdriver
import time
import unittest
import os

@ddt
class Test(unittest.TestCase):

    def setUp(self):
        self.driver = webdriver.Chrome()
        self.driver.implicitly_wait(30)
        # self.file_path = "file:///" + os.path.abspath("")
        self.url = "http://localhost:8080/java_image_server/"
        self.driver.maximize_window()


    def savescreenshot(self,driver,file_name):
        if not os.path.exists("./image"):
            os.makedirs("./image")
        now = time.strftime("%Y%m%d-%H%M%S",time.localtime(time.time()))
        driver.get_screenshot_as_file("./image/"+now+"-"+file_name)
        time.sleep(2)

    # 图片的上传测试
    # 上传的图片有重复的，也有未重复的
    @data("D:\Hui\Pictures\Saved Pictures/222.png","D:\Hui\Pictures\Saved Pictures/我的.png","D:\Hui\Pictures\Saved Pictures/t.png")
    def test_picture(self,value):
        driver = self.driver
        driver.get(self.url)
        # 错误截图,如果没有正确打开要测试的内容，就会保存没有打开测试内容也就是出错的地方
        try:
            self.assertEqual("图片服务器",driver.title)
        except:
            self.savescreenshot(driver,"c.png")
        time.sleep(2)
        driver.find_element_by_xpath("//*[@id='blog-collapse']/form/div[1]/input").send_keys(value)
        time.sleep(2)
        driver.find_element_by_css_selector("#blog-collapse > form > div:nth-child(2) > input").click()
        time.sleep(2)
        driver.switch_to.alert.accept()
        time.sleep(2)



    @unittest.skip("skipping")
    # 图片的删除操作
    @data("//*[@id='container']/div[5]/button","//*[@id='container']/div[2]/button")
    def test_delete(self,value):
        driver = self.driver
        driver.get(self.url)
        # 错误截图,如果没有正确打开要测试的内容，就会保存没有打开测试内容也就是出错的地方
        try:
            self.assertEqual("图片服务器", driver.title)
        except:
            self.savescreenshot(driver, "c.png")
        time.sleep(2)
        driver.find_element_by_xpath(value).click()
        time.sleep(2)
        driver.switch_to.alert.dismiss()
        time.sleep(2)
        driver.find_element_by_xpath(value).click()
        time.sleep(2)
        driver.switch_to.alert.accept()
        time.sleep(2)

    def tearDown(self):
        self.driver.quit()

