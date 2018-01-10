package kaspersky.forms;

import org.openqa.selenium.By;
import webdriver.BaseForm;
import webdriver.elements.Button;

public class MainForm extends BaseForm {

    private static String formLocator = "//div[@class='signin-invite']";
    private Button btnSignIn = new Button(By.xpath("//button[contains(@class, 'primary')][contains(@class, 'signin')]"), "Sign In");

    public MainForm() {
        super(By.xpath(formLocator), "'My Kaspersky' Main Page");
    }

    public void clickSignIn() {
        btnSignIn.clickAndWait();
    }
}
