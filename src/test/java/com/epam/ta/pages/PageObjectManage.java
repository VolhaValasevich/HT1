package com.epam.ta.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PageObjectManage extends AbstractPage {

    private final String BASE_URL = "http://localhost:8080/manage";

    @FindBy (xpath = "//dt[contains(text(), 'Manage Users']")
    private WebElement dt;

    @FindBy (xpath = "//dd[contains(text(), 'Create/delete/modify users that can log in to this Jenkins']")
    private WebElement dd;

    @FindBy(xpath = "//a[contains(@href, 'securityRealm/')]")
    private WebElement manageUsersLink;


    public void openPage() {
        driver.navigate().to(BASE_URL);
    }

    public PageObjectManage(WebDriver driver) {
        super(driver);
    }

    public boolean isManageUsersDtPresent() {
        if (dt != null)
            return true;
        else
            return false;
    }

    public boolean isManageUsersDdPresent() {
        if (dd != null)
            return true;
        else
            return false;
    }

    public boolean isLinkPresent() {
        if (manageUsersLink != null)
            return true;
        else
            return false;
    }

    public PageObjectManage clickManageUsersLink () {
        manageUsersLink.click();
        return this;
    }
}
