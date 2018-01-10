package kaspersky.forms;

import org.openqa.selenium.By;
import webdriver.BaseForm;
import webdriver.elements.Button;
import webdriver.elements.TextBox;

public class SendByMailPopupForm extends BaseForm {

    private static String formLocator = "//form[contains(@class, 'masterAccountForm')]";
    private Button btnSend = new Button(By.xpath("//button[contains(@class, 'send-installer')]"), "Send");
    private TextBox tbEmail = new TextBox(By.id("Email"), "Email");

    public SendByMailPopupForm() {
        super(By.xpath(formLocator), "Send By Post Popup");
    }

    public void clickSend() {
        btnSend.clickAndWait();
    }

    public String getEmail() {
        return tbEmail.getValue();
    }
}
