import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

// *** Custom Functions ***

// Fungsi Login
void login(String username, String password) {
	WebUI.click(findTestObject('Page_CURA Healthcare Service/btn_Menu'))
	WebUI.click(findTestObject('Page_CURA Healthcare Service/a_Login'))
	WebUI.setText(findTestObject('Page_CURA Healthcare Service/input_Username'), username)
	WebUI.setEncryptedText(findTestObject('Page_CURA Healthcare Service/input_Password'), password)
	WebUI.click(findTestObject('Page_CURA Healthcare Service/btn_Login'))
	WebUI.verifyElementVisible(findTestObject('Page_Dashboard/label_Welcome'), FailureHandling.STOP_ON_FAILURE)
}

// Fungsi Membuat Janji
void makeAppointment(String facility, String date, String comment) {
	WebUI.selectOptionByLabel(findTestObject('Page_CURA Healthcare Service/select_Facility'), facility, false)
	WebUI.click(findTestObject('Page_CURA Healthcare Service/input_Hospital_Readmission'))
	WebUI.click(findTestObject('Page_CURA Healthcare Service/input_Visit_Date'))
	WebUI.click(findTestObject("Page_CURA Healthcare Service/td_${date}"))
	WebUI.setText(findTestObject('Page_CURA Healthcare Service/textarea_Comment'), comment)
	WebUI.click(findTestObject('Page_CURA Healthcare Service/btn_BookAppointment'))
	WebUI.verifyElementVisible(findTestObject('Page_Appointment/label_Confirmation'), FailureHandling.STOP_ON_FAILURE)
}

// Fungsi Logout
void logout() {
	WebUI.click(findTestObject('Page_CURA Healthcare Service/btn_Menu'))
	WebUI.click(findTestObject('Page_CURA Healthcare Service/a_Logout'))
	WebUI.verifyElementVisible(findTestObject('Page_Login/btn_Login'), FailureHandling.STOP_ON_FAILURE)
}

// *** Main Test Flow ***

// Buka Browser dan Navigasi ke URL
WebUI.openBrowser('')
WebUI.navigateToUrl('https://katalon-demo-cura.herokuapp.com/')

// Ambil Data Uji dari Test Data
def testData = findTestData('Data Files/AppointmentData')

// Loop untuk Data-Driven Testing
for (int i = 1; i <= testData.getRowNumbers(); i++) {
	// Ambil Data dari Test Data
	String username = testData.getValue('Username', i)
	String password = testData.getValue('Password', i)
	String facility = testData.getValue('Facility', i)
	String date = testData.getValue('Date', i)
	String comment = testData.getValue('Comment', i)

	// Jalankan Skenario Uji
	login(username, password)
	makeAppointment(facility, date, comment)
	logout()
}

// Tutup Browser
WebUI.closeBrowser()

