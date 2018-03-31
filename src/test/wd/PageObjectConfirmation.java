package wd;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PageObjectConfirmation {
    private WebDriverWait wait;
    private final WebDriver driver;

    @FindBy(xpath = "//body")
    private WebElement body;

    @FindBy(xpath = "//span[. = 'Yes']/button")
    private WebElement buttonDelete;

    @FindBy(xpath = "//*[contains(text(), 'Are you sure about deleting the user from Jenkins?')]")
    private WebElement confirmationText;

    public PageObjectConfirmation(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(this.driver, 30);

        if ((!driver.getTitle().equals("Jenkins")) ||
                (!driver.getCurrentUrl().equals("http://localhost:8080/securityRealm/user/someuser/delete"))) {
            throw new IllegalStateException("Wrong site page!");
        }
    }

    public boolean isTextPresent() {
        try {
            body.findElement(By.xpath("//*[contains(text(), 'Are you sure about deleting the user from Jenkins?')]"));
        } catch (NoSuchElementException e) {
            return false;
        }
        return true;
    }

    public PageObjectConfirmation clickDeleteButton () {
        buttonDelete.click();
        return this;
    }

    public boolean checkButtonColor(String color) {
        if (buttonDelete.getCssValue("background-color").equals(color)) {
            return true;
        } else {
            return false;
        }
    }
}
