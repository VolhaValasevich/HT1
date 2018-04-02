package com.epam.ta.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PageObjectManage {

    private WebDriverWait wait;
    private final WebDriver driver;

    @FindBy (xpath = "//dt[contains(text(), 'Manage Users']")
    private WebElement dt;

    @FindBy (xpath = "//dd[contains(text(), 'Create/delete/modify users that can log in to this Jenkins']")
    private WebElement dd;

    @FindBy(xpath = "//a[contains(@href, 'securityRealm/')]")
    private WebElement manageUsersLink;


    public PageObjectManage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(this.driver, 30);

        // Провекрка того факта, что мы на верной странице.
        if ((!driver.getTitle().equals("Manage Jenkins [Jenkins]")) ||
            (!driver.getCurrentUrl().equals("http://localhost:8080/manage"))) {
                throw new IllegalStateException("Wrong site page!");
        }
    }

    public boolean isDtPresent() {
        if (dt != null)
            return true;
        else
            return false;
    }

    public boolean isDdPresent() {
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
