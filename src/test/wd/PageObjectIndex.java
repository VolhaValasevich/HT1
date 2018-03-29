package wd;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PageObjectIndex {
    private WebDriverWait wait;
    private final WebDriver driver;

    @FindBy (linkText = "Manage Jenkins")
    private WebElement manageLink;


    public PageObjectIndex(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(this.driver, 30);

        //Проверка того факта, что мы на верной странице.
        if ((!driver.getTitle().equals("Dashboard [Jenkins]")) ||
                (!driver.getCurrentUrl().equals("http://localhost:8080/"))) {
            throw new IllegalStateException("Wrong site page!");
        }
    }

    public boolean isLinkPresent() {
        if (manageLink != null) {
            return true;
        } else {
            return false;
        }
    }

    public PageObjectIndex clickManageJenkinsLink() {
        manageLink.click();
        return this;
    }
}