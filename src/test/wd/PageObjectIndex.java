package wd;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PageObjectIndex {
    private WebDriverWait wait;
    private final WebDriver driver;

    @FindBy(xpath = "//body")
    private WebElement body;

    @FindBy (linkText = "Manage Jenkins")
    private WebElement manageLink;

    @FindBy(linkText = "ENABLE AUTO REFRESH")
    private WebElement enableAutoRefreshLink;

    @FindBy (linkText = "DISABLE AUTO REFRESH")
    private WebElement disableAutoRefreshLink;


    public PageObjectIndex(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(this.driver, 30);

        //Проверка того факта, что мы на верной странице.
        if ((!driver.getTitle().equals("Dashboard [Jenkins]")) ||
                (!driver.getCurrentUrl().equals("http://localhost:8080/"))) {
            throw new IllegalStateException("Wrong site page!");
        }
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