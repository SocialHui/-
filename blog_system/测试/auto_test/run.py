import aa,bb,cc,dd,ee,ff
import unittest
import sys,os

def createsuite():
    suite = unittest.TestSuite()

    suite.addTest(aa.Login("test_reg"))
    suite.addTest(aa.Login("test_regDel"))

    # makeSuite()组织测试套件
    # suite.addTest(unittest.makeSuite(aa.Login))

    # TestLoader  组织测试套件
    # suite1 = unittest.TestLoader().loadTestsFromTestCase(aa.Login)
    # suite = unittest.TestSuite(suite1)
    return suite

if __name__ == "__main__":
    suite = createsuite()
    runner = unittest.TextTestRunner(verbosity=2)
    runner.run(suite)