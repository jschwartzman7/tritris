#selenium test
import selenium
from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.support.wait import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.common.action_chains import ActionChains

driver = webdriver.Chrome()
driver.get('https://acadinfo.wustl.edu/WSHome/Default.aspx')

# click on login button
driver.find_element(By.NAME, 'ctl00$cphBody$btnLogin').click()

# wait until page loads to access username and password buttons
try:
    username = WebDriverWait(driver, 10).until(EC.presence_of_element_located((By.NAME, "ucWUSTLKeyLogin$txtUsername")))
    password = WebDriverWait(driver, 10).until(EC.presence_of_element_located((By.NAME, "ucWUSTLKeyLogin$txtPassword")))
    loginButton = WebDriverWait(driver, 10).until(EC.presence_of_element_located((By.NAME, "ucWUSTLKeyLogin$btnLogin")))
except:
    driver.quit()

# enter username, password, and login
username.send_keys("j.f.schwartzman")
password.send_keys("Baseball2002*")
loginButton.click()

try:
    courseRegistraion = WebDriverWait(driver, 10).until(EC.presence_of_element_located((By.ID, 'ctl00_mnuHeadMenun4')))
except:
    print('courseRegistration not found')
    driver.quit()
    
#coursesRegistration = driver.find_element(By.XPATH, "//table[@id='ctl00_mnuHeadMenu']/tbody/tr/td[id ='ctl00_mnuHeadMenun4']")
#coursesRegistration = driver.find_element(By.XPATH, "//table[@id='ctl00_mnuHeadMenu']")
#XPath = //tagname[@Attribute=’Value’]

actions = ActionChains(driver)
actions.move_to_element(courseRegistraion)
actions.perform()
try:
    registrationWS = WebDriverWait(driver, 10).until(EC.presence_of_element_located((By.XPATH, "//div[@id='ctl00_mnuHeadMenun4Items']/table/tbody/tr[@id='ctl00_mnuHeadMenun29']")))
except:
    print('registrationWS not found')
    driver.quit()

registrationWS.click()

try:
    #classChoices = WebDriverWait(driver, 5).until(EC.presence_of_element_located((By.XPATH, "//span[@id='grvSecondChoices_ctl02_lblTitle']")))
    classChoices = WebDriverWait(driver, 5).until(EC.presence_of_element_located((By.XPATH, "//form[@id='aspnetForm']/div[@id='ctl00_upWSHome']/div[@id='ctl00_divAccess']/iframe[@id='ctl00_cphBody_genericIFrame']")))#/table[@id='grvSecondChoices']")))
except:
    driver.quit()

print(type(classChoices))
#driver.switch_to.frame(classChoices)
#print(driver.page_source)
#form = driver.find_element(By.XPATH, "//form[@id='form1']")
secondChoices = driver.find_element(By.ID, 'grvSecondChoices_ctl02_lblTitle')
#actions.move_to_element(classChoices)
#actions.perform()
#classChoices.click()
#classes = driver.find_element(By.LINK_TEXT, 'Optimization')
#box = driver.find_element(By.CLASS_NAME, 'mainbox')
#mainbox = driver.find_element(By.ID, "grvSecondChoices")
#classes = [driver.find_elements(By.XPATH, "//table[@id=grvSecondChoices/tbody/tr["+ str(i), + "]") for i in range(1, 10)]
#for course in classes:
    #print(course.title)






while True:
    pass