package kaspersky.forms;

import org.openqa.selenium.By;
import webdriver.BaseForm;
import webdriver.elements.Button;
import webdriver.elements.TextBox;

public class SignInForm extends BaseForm {

    private static String formLocator = "//form[contains(@class, 'signin-form')]";
    private TextBox tbEmail = new TextBox(By.name("EMail"), "Email");
    private TextBox tbPassword = new TextBox(By.name("Password"), "Password");
    private Button btnSignIn = new Button(By.xpath("//button[@data-omniture-cta-name='Sign in']"), "Sign In");

    public SignInForm() {
        super(By.xpath(formLocator), "Sign In");
    }

    public void signIn(String email, String password) {
        tbEmail.setText(email);
        tbPassword.setText(password);
        btnSignIn.clickAndWait();
    }
}
