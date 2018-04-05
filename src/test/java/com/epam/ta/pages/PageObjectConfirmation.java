package com.epam.ta.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PageObjectConfirmation extends AbstractPage {

    private final String BASE_URL = "http://localhost:8080/securityRealm/user/someuser/delete";

    @FindBy(xpath = "//body")
    private WebElement body;

    @FindBy(xpath = "//span[. = 'Yes']/button")
    private WebElement buttonDelete;

    @FindBy(xpath = "//*[contains(text(), 'Are you sure about deleting the user from Jenkins?')]")
    private WebElement confirmationText;

    public void openPage() {
        driver.navigate().to(BASE_URL);
    }

    public PageObjectConfirmation(WebDriver driver) {

        super(driver);
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
