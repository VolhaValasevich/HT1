package wd;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

public class MainTaskTest {
	String base_url = "http://localhost:8080";
	StringBuffer verificationErrors = new StringBuffer();
	WebDriver driver = null;

	@BeforeClass
	public void beforeClass() throws Exception {
		System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		capabilities.setCapability("chrome.switches", Arrays.asList("--homepage=about:blank"));
		driver = new ChromeDriver(capabilities);
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			Assert.fail(verificationErrorString);
		}
	}


	@Test
	public void sampleTest() {
		driver.get(base_url);

		PageObjectAuthorization pageAuthorization = PageFactory.initElements(driver, PageObjectAuthorization.class);
		pageAuthorization.Authorize();

		PageObjectIndex pageIndex = PageFactory.initElements(driver, PageObjectIndex.class);
		Assert.assertTrue(pageIndex.isLinkPresent(), "[No \"Manage Jenkins\" link found!]");
		pageIndex.clickManageJenkinsLink();

		PageObjectManage pageManage = PageFactory.initElements(driver, PageObjectManage.class);
		Assert.assertTrue(pageManage.isDtPresent(), "[No dt element with \"Manage Users\" text found!]");
		try {
			Assert.assertTrue(pageManage.isDdPresent());
		} catch (NoSuchElementException e) {
			verificationErrors.append("[No dd element with \"Create/delete/modify users that can log in" +
					" to this Jenkins\" text found!]\n");
		}
		Assert.assertTrue(pageManage.isLinkPresent(), "[No \"Manage Users\" link found!]");
		pageManage.clickManageUsersLink();

		PageObjectSecurityRealm pageSecurityRealm = PageFactory.initElements(driver, PageObjectSecurityRealm.class);
		Assert.assertTrue(pageSecurityRealm.isCreateUsersLinkPresent(), "[No \"Create User\" link found!]");
		pageSecurityRealm.clickCreateUserLink();

		PageObjectCreateUser pageCreateUser = PageFactory.initElements(driver, PageObjectCreateUser.class);
		try {
			Assert.assertTrue(pageCreateUser.checkEmptyFields());
		} catch (AssertionError e) {
			verificationErrors.append("[Not all fields on Create User page are empty!]\n");
		}
		pageCreateUser.setFields().clickCreateUserButton();

		pageSecurityRealm = PageFactory.initElements(driver, PageObjectSecurityRealm.class);
		try {
			Assert.assertTrue(pageSecurityRealm.isRowPresent());
		} catch (AssertionError e) {
			verificationErrors.append("[No \"someuser\" table cell found!]\n");
		}
		Assert.assertTrue(pageSecurityRealm.isDeleteLinkPresent(), "[No \"Delete someuser\" link found!]");
		pageSecurityRealm.clickDeleteUserLink();

		PageObjectConfirmation pageConfirmation = PageFactory.initElements(driver, PageObjectConfirmation.class);
		try {
			Assert.assertTrue(pageConfirmation.isTextPresent());
		} catch (AssertionError e) {
			verificationErrors.append("[No \"Are you sure about deleting the user from Jenkins?\" text found!]\n");
		}
		pageConfirmation.clickDeleteButton();

		pageSecurityRealm = PageFactory.initElements(driver, PageObjectSecurityRealm.class);
		Assert.assertFalse(pageSecurityRealm.isRowPresent());
		Assert.assertFalse(pageSecurityRealm.isDeleteLinkPresent());
		Assert.assertFalse(pageSecurityRealm.isDeleteAdminLinkPresent());
	}
}
