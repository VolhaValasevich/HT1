package com.epam.ta.pages;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PageObjectCreateUser extends AbstractPage {

    private final String BASE_URL = "http://localhost:8080/securityRealm/addUser";

    @FindBy(xpath = "//body")
    private WebElement body;

    @FindBy (xpath = "//input[@id = 'username' and @type = 'text']")
    private WebElement username;

    @FindBy (xpath = "//input[@name = 'password1' and @type = 'password']")
    private WebElement password1;

    @FindBy (xpath = "//input[@name = 'password2' and @type = 'password']")
    private WebElement password2;

    @FindBy (xpath = "//input[@name = 'fullname' and @type = 'text']")
    private WebElement fullname;

    @FindBy (xpath = "//input[@name = 'email' and @type = 'text']")
    private WebElement email;

    @FindBy (id = "yui-gen3-button")
    private WebElement createButton;

    @FindBy (xpath = "//div[contains(text(), '\"\" is prohibited as a full name for security reasons.')]")
    private WebElement errorText;

    public void openPage() {
        driver.navigate().to(BASE_URL);
    }

    public PageObjectCreateUser(WebDriver driver) {
        super(driver);
    }

    public String getName() {
        return username.getAttribute("value");
    }

    public String getPassword1() {
        return password1.getAttribute("value");
    }

    public String getPassword2() {
        return password2.getAttribute("value");
    }

    public String getFullName() {
        return fullname.getAttribute("value");
    }

    public String getEmail() {
        return email.getAttribute("value");
    }

    //this is horrible, but I wanted every possible error to be followed by a specific message
    public String checkFields () {
        StringBuffer buffer = new StringBuffer();
        try {
            if (!getName().equals("")) {
                buffer.append("['Username' field is not empty!]\n");
            }
        } catch (NoSuchElementException e) {
            buffer.append("['Username' field not found!]\n");
        }
        try {
            if (!getPassword1().equals("")) {
                buffer.append("['Password' field is not empty!]\n");
            }
        } catch (NoSuchElementException e) {
            buffer.append("['Password' field not found!]\n");
        }
        try {
            if (!getPassword2().equals("")) {
                buffer.append("['Confirm password' field is not empty!]\n");
            }
        } catch (NoSuchElementException e) {
            buffer.append("['Confirm password' field not found!]\n");
        }
        try {
            if (!getFullName().equals("")) {
                buffer.append("['Full name' field is not empty!]\n");
            }
        } catch (NoSuchElementException e) {
            buffer.append("['Full name' field not found!]\n");
        }
        try {
            if (!getEmail().equals("")) {
                buffer.append("['E-mail' field is not empty!]\n");
            }
        } catch (NoSuchElementException e) {
            buffer.append("['E-mail' field not found!]\n");
        }
        if (buffer.length() == 0) {
            return "true";
        } else {
            return buffer.toString();
        }
    }

    public PageObjectCreateUser setName(String value) {
        username.sendKeys(value);
        return this;
    }

    public PageObjectCreateUser setPassword1(String value) {
        password1.sendKeys(value);
        return this;
    }

    public PageObjectCreateUser setPassword2(String value) {
        password2.sendKeys(value);
        return this;
    }

    public PageObjectCreateUser setFullName(String value) {
        fullname.sendKeys(value);
        return this;
    }

    public PageObjectCreateUser setEmail(String value) {
        email.sendKeys(value);
        return this;
    }

    public PageObjectCreateUser setFields (String username, String password, String confirmpassword, String fullname, String email) {
        setName(username);
        setPassword1(password);
        setPassword2(confirmpassword);
        setFullName(fullname);
        setEmail(email);
        return this;
    }

    public PageObjectCreateUser clickCreateUserButton() {
        createButton.click();
        return this;
    }

    public boolean checkButtonColor(String color) {
        if (createButton.getCssValue("background-color").equals(color)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean errorTextIsPresent() {
        try {
            errorText.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
        return true;
    }
}
