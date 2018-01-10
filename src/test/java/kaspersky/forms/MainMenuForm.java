package kaspersky.forms;

import org.openqa.selenium.By;
import webdriver.BaseForm;
import webdriver.elements.Link;

public class MainMenuForm extends BaseForm {

    private static String formLocator = "//ul[contains(@class,'priority-menu')]";
    private String lnkDownloadsLocator = "//div[contains(@class,  'submenu')]//a[contains(@data-tracking-action,'%1$s')]";

    public MainMenuForm() {
        super(By.xpath(formLocator), "Main Menu");
    }

    public void clickMenuItem(MainMenuItems menuItem) {
        new Link(By.xpath(String.format(lnkDownloadsLocator, menuItem)), menuItem.toString()).clickAndWait();
    }
}


