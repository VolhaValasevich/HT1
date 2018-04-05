package com.epam.ta.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PageObjectIndex extends AbstractPage {

    private final String BASE_URL = "http://localhost:8080/";

    @FindBy(xpath = "//body")
    private WebElement body;

    @FindBy (linkText = "Manage Jenkins")
    private WebElement manageLink;

    @FindBy(linkText = "ENABLE AUTO REFRESH")
    private WebElement enableAutoRefreshLink;

    @FindBy (linkText = "DISABLE AUTO REFRESH")
    private WebElement disableAutoRefreshLink;


    public void openPage() {
        driver.navigate().to(BASE_URL);
    }

    public PageObjectIndex(WebDriver driver) {
        super(driver);
    }

    public boolean isManageLinkPresent() {
        try {
            body.findElement(By.linkText("Manage Jenkins"));
        } catch (NoSuchElementException e) {
            return false;
        }
        return true;
    }

    public PageObjectIndex clickManageJenkinsLink() {
        manageLink.click();
        return this;
    }

    public boolean isEnableRefreshLinkPresent() {
        try {
            body.findElement(By.linkText("ENABLE AUTO REFRESH"));
        } catch (NoSuchElementException e) {
            return false;
        }
        return true;
    }

    public boolean isDisableRefreshLinkPresent() {
        try {
            body.findElement(By.linkText("DISABLE AUTO REFRESH"));
        } catch (NoSuchElementException e) {
            return false;
        }
        return true;
    }

    public PageObjectIndex enableAutoRefresh() {
        enableAutoRefreshLink.click();
        return this;
    }

    public PageObjectIndex disableAutoRefresh() {
        disableAutoRefreshLink.click();
        return this;
    }
}