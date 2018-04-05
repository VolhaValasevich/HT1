package com.epam.ta.steps;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.TimeUnit;

import com.epam.ta.driver.DriverSingleton;
import com.epam.ta.pages.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;

public class Steps
{
	private WebDriver driver;

	private final Logger logger = LogManager.getRootLogger();

	public void initBrowser()
	{
		driver = DriverSingleton.getDriver();
	}

	public void closeDriver()
	{
		DriverSingleton.closeDriver();
	}

	public void logIn(String username, String password)
	{
	    PageObjectAuthorization pageAuthorization = PageFactory.initElements(driver, PageObjectAuthorization.class);
        pageAuthorization.openPage();
        pageAuthorization.Authorize(username, password);
	}

	public boolean checkManageJenkinsLinkPresent() {
        PageObjectIndex pageIndex = PageFactory.initElements(driver, PageObjectIndex.class);
        return pageIndex.isManageLinkPresent();
	}

	public boolean checkManageUsersDtPresent() {
        PageObjectIndex pageIndex = PageFactory.initElements(driver, PageObjectIndex.class);
        pageIndex.clickManageJenkinsLink();
        PageObjectManage pageManage = PageFactory.initElements(driver, PageObjectManage.class);
        return pageManage.isManageUsersDtPresent();
    }

    public boolean checkManageUsersDdPresent() {
        PageObjectIndex pageIndex = PageFactory.initElements(driver, PageObjectIndex.class);
        pageIndex.clickManageJenkinsLink();
        PageObjectManage pageManage = PageFactory.initElements(driver, PageObjectManage.class);
        return pageManage.isManageUsersDdPresent();
    }

    public boolean checkCreateUserLinkPresent() {
        PageObjectManage pageManage = PageFactory.initElements(driver, PageObjectManage.class);
        pageManage.clickManageUsersLink();
        PageObjectSecurityRealm pageSecurityRealm = PageFactory.initElements(driver, PageObjectSecurityRealm.class);
        return pageSecurityRealm.isCreateUsersLinkPresent();
    }

    public String checkCreateUserFields() {
        PageObjectSecurityRealm pageSecurityRealm = PageFactory.initElements(driver, PageObjectSecurityRealm.class);
        pageSecurityRealm.clickCreateUserLink();
        PageObjectCreateUser pageCreateUser = PageFactory.initElements(driver, PageObjectCreateUser.class);
        return pageCreateUser.checkFields();
    }

    public boolean createUser(String username, String password, String confirmpassword, String fullname, String email) {
        PageObjectCreateUser pageCreateUser = PageFactory.initElements(driver, PageObjectCreateUser.class);
        pageCreateUser.setFields(username, password, confirmpassword, fullname, email).clickCreateUserButton();
        PageObjectSecurityRealm pageSecurityRealm = PageFactory.initElements(driver, PageObjectSecurityRealm.class);
        return pageSecurityRealm.isRowPresent();
    }

    public boolean deleteUser() {
        PageObjectSecurityRealm pageSecurityRealm = PageFactory.initElements(driver, PageObjectSecurityRealm.class);
        pageSecurityRealm.clickDeleteUserLink();
        PageObjectConfirmation pageConfirmation = PageFactory.initElements(driver, PageObjectConfirmation.class);
        return pageConfirmation.isTextPresent();
    }

    public String deleteUserConfirmation() {
        StringBuffer buffer = new StringBuffer();
        PageObjectConfirmation pageConfirmation = PageFactory.initElements(driver, PageObjectConfirmation.class);
        pageConfirmation.clickDeleteButton();
        PageObjectSecurityRealm pageSecurityRealm = PageFactory.initElements(driver, PageObjectSecurityRealm.class);
        if (pageSecurityRealm.isRowPresent()) {
            buffer.append("[The table row with \"someuser\" cell is present after deleting someuser!]\n");
        }
        if (pageSecurityRealm.isDeleteLinkPresent()) {
            buffer.append("[The \"user/someuser/delete\" link is present after deleting someuser!]\n");
        }
        if (buffer.length() == 0) {
            return "true";
        } else {
            return buffer.toString();
        }
    }

    public boolean checkDeleteAdminLinkPresent() {
        PageObjectSecurityRealm pageSecurityRealm = PageFactory.initElements(driver, PageObjectSecurityRealm.class);
        pageSecurityRealm.openPage();
        return pageSecurityRealm.isDeleteAdminLinkPresent();
    }

    public boolean buttonAddUserColorCheck (String color) {
        PageObjectCreateUser pageCreateUser = PageFactory.initElements(driver, PageObjectCreateUser.class);
        pageCreateUser.openPage();
        return pageCreateUser.checkButtonColor(color);
    }

    public boolean buttonConfirmationColorCheck(String color) {
        PageObjectConfirmation pageConfirmation = PageFactory.initElements(driver, PageObjectConfirmation.class);
        pageConfirmation.openPage();
        return pageConfirmation.checkButtonColor(color);
    }

    public boolean createUserWithEmptyName (String username, String password, String confirmpassword, String fullname, String email) {
        PageObjectCreateUser pageCreateUser = PageFactory.initElements(driver, PageObjectCreateUser.class);
        pageCreateUser.openPage();
        pageCreateUser.setFields(username, password, confirmpassword, fullname, email).clickCreateUserButton();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return pageCreateUser.errorTextIsPresent();
    }

    public boolean enableAutoRefresh() {
        PageObjectIndex pageIndex = PageFactory.initElements(driver, PageObjectIndex.class);
        pageIndex.enableAutoRefresh();
        return pageIndex.isDisableRefreshLinkPresent();
    }

    public boolean disableAutoRefresh() {
        PageObjectIndex pageIndex = PageFactory.initElements(driver, PageObjectIndex.class);
        pageIndex.disableAutoRefresh();
        return pageIndex.isEnableRefreshLinkPresent();
    }
}
