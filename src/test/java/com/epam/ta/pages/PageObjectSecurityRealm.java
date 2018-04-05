package com.epam.ta.pages;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PageObjectSecurityRealm extends AbstractPage {

    private final String BASE_URL = "http://localhost:8080/securityRealm/";

    @FindBy(xpath = "//body")
    private WebElement body;

    @FindBy (linkText = "Create User")
    private WebElement createUserLink;

    @FindBy (xpath = "//table[@id='people']/tbody/.//td[.//text()[contains(., 'someuser')]]")
    private WebElement tableCell;

    @FindBy (xpath = "//a[@href = 'user/someuser/delete']")
    private WebElement deleteSomeuserLink;

    @FindBy (xpath = "//a[@href = 'user/admin/delete']")
    private WebElement deleteAdminLink;

    public void openPage() {
        driver.navigate().to(BASE_URL);
    }

    public PageObjectSecurityRealm(WebDriver driver) {
        super(driver);
    }

    public boolean isCreateUsersLinkPresent() {
        try {
            createUserLink.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
        return true;
    }

    public PageObjectSecurityRealm clickCreateUserLink () {
        createUserLink.click();
        return this;
    }

    public boolean isRowPresent() {
        try {
            tableCell.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
        return true;
    }

    public boolean isDeleteLinkPresent() {
        try {
            deleteSomeuserLink.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
        return true;
    }

    public boolean isDeleteAdminLinkPresent() {
        try {
            deleteAdminLink.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
        return true;
    }

    public PageObjectSecurityRealm clickDeleteUserLink() {
        deleteSomeuserLink.click();
        return this;
    }
}
