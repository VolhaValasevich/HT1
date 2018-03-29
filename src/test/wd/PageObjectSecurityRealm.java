package wd;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PageObjectSecurityRealm {

    private WebDriverWait wait;
    private final WebDriver driver;

    @FindBy(xpath = "//body")
    private WebElement body;

    @FindBy (linkText = "Create User")
    private WebElement createUserLink;

    private WebElement deleteSomeuserLink;
    private WebElement deleteAdminLink;

    public PageObjectSecurityRealm(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(this.driver, 30);

        // Провекрка того факта, что мы на верной странице.
        if ((!driver.getTitle().equals("Users [Jenkins]")) ||
                (!driver.getCurrentUrl().equals("http://localhost:8080/securityRealm/"))) {
            throw new IllegalStateException("Wrong site page!");
        }
    }

    public boolean isCreateUsersLinkPresent() {
        if (createUserLink != null) {
            return true;
        } else {
            return false;
        }
    }

    public PageObjectSecurityRealm clickCreateUserLink () {
        createUserLink.click();
        return this;
    }

    public boolean isRowPresent() {
        try {
            body.findElement(By.xpath("//table[@id='people']/tbody/.//td[.//text()[contains(., 'someuser')]]"));
        } catch (NoSuchElementException e) {
            return false;
        }
        return true;
    }

    public boolean isDeleteLinkPresent() {
        try {
            deleteSomeuserLink = body.findElement(By.xpath("//a[@href = 'user/someuser/delete']"));
        } catch (NoSuchElementException e) {
            return false;
        }
        return true;
    }

    public boolean isDeleteAdminLinkPresent() {
        try {
            deleteAdminLink = body.findElement(By.xpath("//a[@href = 'user/admin/delete']"));
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