package com.epam.ta.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PageObjectAuthorization extends AbstractPage {

    private final String BASE_URL = "http://localhost:8080/login?from=%2F";

    @FindBy (id = "j_username")
    private WebElement inputUsername;

    @FindBy (name = "j_password")
    private WebElement inputPassword;

    @FindBy (id = "yui-gen1-button")
    private WebElement buttonLogIn;

    public void openPage() {
        driver.navigate().to(BASE_URL);
    }

    public PageObjectAuthorization(WebDriver driver) {
        super(driver);
    }

    public PageObjectAuthorization setUsername(String value) {
        inputUsername.clear();
        inputUsername.sendKeys(value);
        return this;
    }

    public PageObjectAuthorization setPassword(String value) {
        inputPassword.clear();
        inputPassword.sendKeys(value);
        return this;
    }

    public PageObjectAuthorization Authorize(String login, String password) {
        setUsername(login);
        setPassword(password);
        buttonLogIn.click();
        return this;
    }


}