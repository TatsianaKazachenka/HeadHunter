package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage{

    @FindBy(xpath = "//*[@id='HH-React-Root']//*[@data-qa='login-input-username']")
    WebElement inputLogin;
    @FindBy(xpath = "//*[@id='HH-React-Root']//*[@data-qa='login-input-password']")
    WebElement inputPassword;
    @FindBy(xpath = "//*[@id='HH-React-Root']//*[@data-qa='account-login-submit']")
    WebElement buttonLogin;


    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void openPage() {
        openPage(BASE_HH_URL + LOGIN_URL);
    }

    public void login(String login, String password) {
        inputLogin.sendKeys(login);
        inputPassword.sendKeys(password);
        buttonLogin.click();
    }
}
