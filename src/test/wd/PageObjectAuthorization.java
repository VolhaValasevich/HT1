package wd;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PageObjectAuthorization {
    private WebDriverWait wait;
    private final WebDriver driver;
    //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!ЗАМЕНИТЬ!
    private String login = "volhavalasevich";
    private String password = "9771Erynlasgalen";
    //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

    @FindBy (id = "j_username")
    private WebElement inputUsername;

    @FindBy (name = "j_password")
    private WebElement inputPassword;

    @FindBy (id = "yui-gen1-button")
    private WebElement buttonLogIn;

    public PageObjectAuthorization(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(this.driver, 30);

       // Провекрка того факта, что мы на верной странице.
        if ((!driver.getTitle().equals("Jenkins")) ||
            (!driver.getCurrentUrl().equals("http://localhost:8080/login?from=%2F"))) {
            throw new IllegalStateException("Wrong site page!");
         }
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

    public PageObjectAuthorization Authorize() {
        setUsername(login);
        setPassword(password);
        buttonLogIn.click();
        return this;
    }


}