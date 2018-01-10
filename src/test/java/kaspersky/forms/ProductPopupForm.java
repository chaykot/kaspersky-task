package kaspersky.forms;

import org.openqa.selenium.By;
import webdriver.BaseForm;
import webdriver.elements.Button;
import webdriver.elements.Link;

public class ProductPopupForm extends BaseForm {

    private static String formLocator = "//div[contains(@class,'downloadDialog')]";
    private Button btnDownload = new Button(By.xpath("//button[@data-omniture-cta-name='Download button']"), "Download");
    private Link lnkSendByPost = new Link(By.xpath("//div[contains(@class, 'sendLink')]"), "Send Link");

    public ProductPopupForm() {
        super(By.xpath(formLocator), "Product Popup");
    }

    public String getDownloadHref() {
        return btnDownload.getAttribute("data-link");
    }

    public void clickSendByMail() {
        lnkSendByPost.clickAndWait();
    }
}
