package com.epam.ta;

import com.epam.ta.pages.PageObjectAuthorization;
import com.epam.ta.pages.PageObjectConfirmation;
import com.epam.ta.pages.PageObjectCreateUser;
import com.epam.ta.pages.PageObjectIndex;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class AdditionalTasksTest {
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
    }

    /*@BeforeTest
    public void beforeTest() {
        driver.get(base_url);
        if (driver.getCurrentUrl().equals("http://localhost:8080/login?from=%2F")) {
            PageObjectAuthorization pageAuthorization = PageFactory.initElements(driver, PageObjectAuthorization.class);
            pageAuthorization.Authorize();
        }
    }*/

    @AfterTest
    public void afterTest() {
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            Assert.fail(verificationErrorString);
        }
    }

    @Test
    public void buttonsBackgroundTest() {
        driver.get(base_url);
        if (driver.getCurrentUrl().equals("http://localhost:8080/login?from=%2F")) {
            PageObjectAuthorization pageAuthorization = PageFactory.initElements(driver, PageObjectAuthorization.class);
            pageAuthorization.Authorize();
        }
        driver.get(base_url + "/securityRealm/addUser");
        String color = "rgba(75, 117, 139, 1)";
        PageObjectCreateUser pageCreateUser = PageFactory.initElements(driver, PageObjectCreateUser.class);
        try {
            Assert.assertTrue(pageCreateUser.checkButtonColor(color));
        } catch (AssertionError e) {
            verificationErrors.append("[The button on 'Create User' page has wrong background color!]\n");
        }
        pageCreateUser.setFields().clickCreateUserButton();
        driver.get(base_url + "/securityRealm/user/someuser/delete");
        PageObjectConfirmation pageConfirmation = PageFactory.initElements(driver, PageObjectConfirmation.class);
        try {
            Assert.assertTrue(pageConfirmation.checkButtonColor(color));
        } catch (AssertionError e) {
            verificationErrors.append("[The button on 'Delete User' page has wrong background color!]\n");
        }
    }

    @Test
    public void createEmptyUserNameTest() throws InterruptedException {
        driver.get(base_url);
        if (driver.getCurrentUrl().equals("http://localhost:8080/login?from=%2F")) {
            PageObjectAuthorization pageAuthorization = PageFactory.initElements(driver, PageObjectAuthorization.class);
            pageAuthorization.Authorize();
        }
        driver.get(base_url + "/securityRealm/addUser");
        PageObjectCreateUser pageCreateUser = PageFactory.initElements(driver, PageObjectCreateUser.class);
        pageCreateUser.setFieldsEmptyName().clickCreateUserButton();
        Assert.assertTrue(pageCreateUser.errorTextIsPresent(), "[No correct error message is displayed after creating" +
                " a user with an empty username!]");
    }

    @Test
    public void enableDisableAutoRefreshChangeTest() {
        driver.get(base_url);
        if (driver.getCurrentUrl().equals("http://localhost:8080/login?from=%2F")) {
            PageObjectAuthorization pageAuthorization = PageFactory.initElements(driver, PageObjectAuthorization.class);
            pageAuthorization.Authorize();
        }
        PageObjectIndex pageIndex = PageFactory.initElements(driver, PageObjectIndex.class);
        for (int i=0; i<2; i++) {
            if (pageIndex.isEnableRefreshLinkPresent()) {
                pageIndex.enableAutoRefresh();
                Assert.assertTrue(pageIndex.isDisableRefreshLinkPresent(), "[The 'DISABLE AUTO REFRESH' link does not " +
                        "appear after enabling auto refresh!\n]");
            } else if (pageIndex.isDisableRefreshLinkPresent()) {
                pageIndex.disableAutoRefresh();
                Assert.assertTrue(pageIndex.isEnableRefreshLinkPresent(), "[The 'ENABLE AUTO REFRESH' link does not " +
                        "appear after disabling auto refresh!\n]");
            } else {
                Assert.fail("[No 'ENABLE AUTO REFRESH' or 'DISABLE AUTO REFRESH' link found!]");
            }
        }
    }
}
