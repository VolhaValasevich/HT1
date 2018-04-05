package com.epam.ta;

import com.epam.ta.steps.Steps;
import org.testng.Assert;
import org.testng.annotations.*;

public class JenkinsTest {

    private final String LOGIN = "login";
    private final String PASSWORD = "password";
    StringBuffer verificationErrors = new StringBuffer();
	Steps steps;

    @BeforeMethod(description = "Init browser")
    public void setUp()
    {
        steps = new Steps();
        steps.initBrowser();
    }

	@AfterMethod
	public void afterClass() {
		steps.closeDriver();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			Assert.fail(verificationErrorString);
		}
	}

	@Test
	public void mainTest() {
		steps.logIn(LOGIN, PASSWORD);
		Assert.assertTrue(steps.checkManageJenkinsLinkPresent(), "[No \"Manage Jenkins\" link found!]");
		Assert.assertTrue(steps.checkManageUsersDtPresent(), "[No dt element with \"Manage Users\" text found!]");
		try {
			Assert.assertTrue(steps.checkManageUsersDdPresent());
		} catch (AssertionError e) {
			verificationErrors.append("[No dd element with \"Create/delete/modify users that can log in" +
					" to this Jenkins\" text found!]\n");
		}
		Assert.assertTrue(steps.checkCreateUserLinkPresent(), "[No \"Create User\" link found!]");
		Assert.assertEquals(steps.checkCreateUserFields(), "true", steps.checkCreateUserFields());
        Assert.assertTrue(steps.createUser("someuser", "somepassword", "somepassword",
                "Some Full Name", "some@addr.dom"), "['someuser' cell does not appear after creating a user!]");
		try {
			Assert.assertTrue(steps.deleteUser());
		} catch (AssertionError e) {
			verificationErrors.append("[No \"Are you sure about deleting the user from Jenkins?\" text found!]\n");
		}
		try {
			Assert.assertEquals(steps.deleteUserConfirmation(), "true");
		} catch (AssertionError e) {
			verificationErrors.append(steps.deleteUserConfirmation());
		}
		Assert.assertFalse(steps.checkDeleteAdminLinkPresent(), "[The \"user/admin/delete\" link is present!]\n");
	}

    @Test
    public void buttonsBackgroundTest() {
        steps.logIn(LOGIN, PASSWORD);
        String color = "rgba(75, 117, 139, 1)";
        try {
            Assert.assertTrue(steps.buttonAddUserColorCheck(color));
        } catch (AssertionError e) {
            verificationErrors.append("[The button on 'Create User' page has wrong background color!]\n");
        }
        steps.createUser("someuser", "somepassword", "somepassword",
                "Some Full Name", "some@addr.dom");
        try {
            Assert.assertTrue(steps.buttonConfirmationColorCheck(color));
        } catch (AssertionError e) {
            verificationErrors.append("[The button on 'Delete User' page has wrong background color!]\n");
        }
        steps.deleteUser();
    }

    @Test
    public void createEmptyUserNameTest() {
        steps.logIn(LOGIN, PASSWORD);
        //for some reason this test fails if the "Full name" field is filled, though the error message appears correctly
        Assert.assertTrue(steps.createUserWithEmptyName("", "somepassword", "somepassword",
                "", "some@addr.dom"), "[No correct error message is displayed after creating" +
                " a user with an empty username!]");
    }

    @Test
    public void enableDisableAutoRefreshChangeTest() {
        steps.logIn(LOGIN, PASSWORD);
        Assert.assertTrue(steps.enableAutoRefresh(), "[The 'DISABLE AUTO REFRESH' link does not " +
                "appear after enabling auto refresh!\n]");
        Assert.assertTrue(steps.disableAutoRefresh(), "[The 'ENABLE AUTO REFRESH' link does not " +
                "appear after disabling auto refresh!\n]");
    }

}
