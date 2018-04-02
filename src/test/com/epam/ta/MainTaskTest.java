package com.epam.ta;

import com.epam.ta.pages.*;
import org.openqa.selenium.WebDriver;
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
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
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
	public void mainTest() {
		driver.get(base_url);

		//Authorization (if needed)
		if (driver.getCurrentUrl().equals("http://localhost:8080/login?from=%2F")) {
			PageObjectAuthorization pageAuthorization = PageFactory.initElements(driver, PageObjectAuthorization.class);
			pageAuthorization.Authorize();
		}

		//1.1) Checking if "Manage Jenkins" link is present and clicking on it
		PageObjectIndex pageIndex = PageFactory.initElements(driver, PageObjectIndex.class);
		Assert.assertTrue(pageIndex.isManageLinkPresent(), "[No \"Manage Jenkins\" link found!]");
		pageIndex.clickManageJenkinsLink();

		//1.2) Checking if dt element "Manage Users" and dd element with text are present; clicking
		//   on "Manage Users" link
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

		//2) Checking if "Create User" link is present and clicking on it
		PageObjectSecurityRealm pageSecurityRealm = PageFactory.initElements(driver, PageObjectSecurityRealm.class);
		Assert.assertTrue(pageSecurityRealm.isCreateUsersLinkPresent(), "[No \"Create User\" link found!]");
		pageSecurityRealm.clickCreateUserLink();

		//3) Checking if user creation form contains all the necessary empty fields, filling them and clicking on
		//   "Create User" button
		PageObjectCreateUser pageCreateUser = PageFactory.initElements(driver, PageObjectCreateUser.class);
		Assert.assertEquals(pageCreateUser.checkFields(), "true", pageCreateUser.checkFields());
		pageCreateUser.setFields().clickCreateUserButton();

		//4) Checking if tr element with td cell and "someuser" text is present
		//5.1) Checking if "Delete someuser" link is present and clicking on it
		pageSecurityRealm = PageFactory.initElements(driver, PageObjectSecurityRealm.class);
		try {
			Assert.assertTrue(pageSecurityRealm.isRowPresent());
		} catch (AssertionError e) {
			verificationErrors.append("[No \"someuser\" table cell found!]\n");
		}
		Assert.assertTrue(pageSecurityRealm.isDeleteLinkPresent(), "[No \"Delete someuser\" link found!]");
		pageSecurityRealm.clickDeleteUserLink();

		//5.2) Checking if confirmation text is present
		//6.1) Clicking on "Yes button"
		PageObjectConfirmation pageConfirmation = PageFactory.initElements(driver, PageObjectConfirmation.class);
		try {
			Assert.assertTrue(pageConfirmation.isTextPresent());
		} catch (AssertionError e) {
			verificationErrors.append("[No \"Are you sure about deleting the user from Jenkins?\" text found!]\n");
		}
		pageConfirmation.clickDeleteButton();

		//6.2) Checking if tr and td element with "someuser" text and "delete someuser" link are NOT present anymore
		pageSecurityRealm = PageFactory.initElements(driver, PageObjectSecurityRealm.class);
		try {
			Assert.assertFalse(pageSecurityRealm.isRowPresent());
		} catch (AssertionError e) {
			verificationErrors.append("[The table row with \"someuser\" cell is present after deleting someuser!]\n");
		}
		try {
			Assert.assertFalse(pageSecurityRealm.isDeleteLinkPresent());
		} catch (AssertionError e) {
			verificationErrors.append("[The \"user/someuser/delete\" link is present after deleting someuser!]\n");
		}

		//7) Checking if "user/admin/delete" is not present
		Assert.assertFalse(pageSecurityRealm.isDeleteAdminLinkPresent(), "[The \"user/admin/delete\" link is present!]\n");
	}
}
