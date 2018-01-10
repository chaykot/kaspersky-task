package kaspersky.forms;

import org.openqa.selenium.By;
import webdriver.BaseForm;
import webdriver.elements.Button;
import webdriver.elements.Link;

public class DownloadsForm extends BaseForm {

    private static String formLocator = "//div[contains(@id, 'downloads')]";
    private String lnkOsTitleLocator = "//div[contains(@class, 'title')][contains(.,'%s')]";
    private String btnDownloadLocator = "//div[contains(.,'%s')]/ancestor::div[contains(@class, 'downloadProgramCard')]//button[@tabindex='0']";

    public DownloadsForm() {
        super(By.xpath(formLocator), "Downloads");
    }

    public void chooseOS(String operationSystem) {
        new Link(By.xpath(String.format(lnkOsTitleLocator, operationSystem)), operationSystem).clickAndWait();
    }

    public void clickDownloadButtonForProduct(String product) {
        new Button(By.xpath(String.format(btnDownloadLocator, product)), product).waitAndClick();
    }

    public void downloadProduct(String operationSystem, String product) {
        chooseOS(operationSystem);
        clickDownloadButtonForProduct(product);
    }
}
