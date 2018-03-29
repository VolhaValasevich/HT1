package wd;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PageObjectCreateUser {
    private WebDriverWait wait;
    private final WebDriver driver;

    @FindBy (xpath = "//input[@id = 'username' and @type = 'text']")
    private WebElement username;

    @FindBy (xpath = "//input[@name = 'password1' and @type = 'password']")
    private WebElement password1;

    @FindBy (xpath = "//input[@name = 'password2' and @type = 'password']")
    private WebElement password2;

    @FindBy (xpath = "//input[@name = 'fullname' and @type = 'text']")
    private WebElement fullname;

    @FindBy (xpath = "//input[@name = 'email' and @type = 'text']")
    private WebElement email;

    @FindBy (id = "yui-gen3-button")
    private WebElement createButton;

    public PageObjectCreateUser(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(this.driver, 30);

        // Провекрка того факта, что мы на верной странице.
        /*if ((!driver.getTitle().equals("Create User [Jenkins]")) ||
                (!driver.getCurrentUrl().equals("http://localhost:8080/securityRealm/createAccountByAdmin"))) {
            throw new IllegalStateException("Wrong site page!");
        }*/
    }

    public String getName() {
        return username.getAttribute("value");
    }

    public String getPassword1() {
        return password1.getAttribute("value");
    }

    public String getPassword2() {
        return password2.getAttribute("value");
    }

    public String getFullName() {
        return fullname.getAttribute("value");
    }

    public String getEmail() {
        return email.getAttribute("value");
    }

    public boolean checkEmptyFields () {
        if (getName().equals("") && getPassword1().equals("") && getPassword2().equals("") && getFullName().equals("")
                && getEmail().equals(""))
            return true;
        else
            return false;
    }

    public PageObjectCreateUser setName(String value) {
        username.sendKeys(value);
        return this;
    }

    public PageObjectCreateUser setPassword1(String value) {
        password1.sendKeys(value);
        return this;
    }

    public PageObjectCreateUser setPassword2(String value) {
        password2.sendKeys(value);
        return this;
    }

    public PageObjectCreateUser setFullName(String value) {
        fullname.sendKeys(value);
        return this;
    }

    public PageObjectCreateUser setEmail(String value) {
        email.sendKeys(value);
        return this;
    }

    public PageObjectCreateUser setFields () {
        setName("someuser");
        setPassword1("somepassword");
        setPassword2("somepassword");
        setFullName("Some Full Name");
        setEmail("some@addr.dom");
        return this;
    }

    public PageObjectCreateUser clickCreateUserButton() {
        createButton.click();
        return this;
    }
}
