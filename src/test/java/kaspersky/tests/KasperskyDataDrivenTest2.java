package kaspersky.tests;

import kaspersky.forms.*;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import webdriver.BaseTestDataDriven;
import webdriver.utils.MailUtils;

import javax.mail.MessagingException;
import java.io.File;
import java.lang.reflect.Method;

public class KasperskyDataDrivenTest2 extends BaseTestDataDriven {

    private String username;
    private String password;
    private String os;
    private String product;
    private final String MAIL_FOLDER = "INBOX";
    private String messageSubject = "Ссылки для скачиванияKaspersky %s";

    @DataProvider(name = "DataProv")
    static Object[][] makeData(final Method testMethod) throws IllegalArgumentException {
        Object[][] retObjArr = getTableArray((System.getProperty("user.dir")+File.separator+props.getProperty("testDataPath")).replace("\\target", ""));
        return retObjArr;
    }

    @Test(dataProvider = "DataProv")
    public void readParams(String username, String password, String os, String product) throws Throwable {
        this.username = username;
        this.password = password;
        this.os = os;
        this.product = product;
        runTest();
    }

    @Override
    public void runTest(String... args) throws MessagingException {
        LogStep("Click Sign In");
        MainForm mainForm = new MainForm();
        mainForm.clickSignIn();

        LogStep("Sign In");
        SignInForm signInForm = new SignInForm();
        signInForm.signIn(username, password);

        LogStep("Go to Downloads");
        MainMenuForm mainMenuForm = new MainMenuForm();
        mainMenuForm.clickMenuItem(MainMenuItems.DOWNLOADS);

        LogStep("Click Download");
        DownloadsForm downloadsForm = new DownloadsForm();
        downloadsForm.downloadProduct(os, product);

        LogStep("Remember Download Link");
        ProductPopupForm productPopupForm = new ProductPopupForm();
        String downloadHref = productPopupForm.getDownloadHref();

        LogStep("Click Send By Mail");
        productPopupForm.clickSendByMail();

        LogStep("Click Send");
        SendByMailPopupForm sendByMailPopupForm = new SendByMailPopupForm();
        Assert.assertEquals(sendByMailPopupForm.getEmail(), username, String.format("Email is not %s", username));
        sendByMailPopupForm.clickSend();

        LogStep("Check Mail");
        String messageContent = new MailUtils(username, password).getMessageContent(MAIL_FOLDER, String.format(messageSubject, product));
        Assert.assertTrue(decodeMail(messageContent).contains(downloadHref), "Mail does not contain correct link for downloading");
    }

    @BeforeMethod
    public void beforeTest() {
        getBrowser().navigate(getBrowser().getStartBrowserURL());
        stepNumber = 1;
    }

    @AfterMethod
    public void afterTest() {
        MailUtils mailUtils = new MailUtils(username, password);
        mailUtils.deleteAllMessages(MAIL_FOLDER);
        getBrowser().exit();
    }

    @Override
    public boolean shouldAnalys() {
        return false;
    }

    @Override
    public void invokeAnalys(Throwable exc, String bodyText) throws Throwable {
    }

    private String decodeMail(String mail) {
        return mail.replace("%2F", "/").replace("%3F", "?").replace("%3D", "=").replace("%25", "%");
    }
}
