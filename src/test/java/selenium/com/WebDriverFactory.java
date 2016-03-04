package selenium.com;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * WebDriverFactoryクラス
 * @author panasonic
 */
public class WebDriverFactory {
    /** Logger */
    private static final Logger logger = LoggerFactory.getLogger(WebDriverFactory.class);

    /** プロパティ */
    protected static SeleniumPropertyManager prop = SeleniumPropertyManager.INSTANCE;

    /**
     * Webブラウザタイプ
     */
    public static enum Browser {
        IE, CHROME, FIREFOX;
    }

    /**
     * WebDriverの生成
     * @param browser Webブラウザタイプ
     * @return WebDriver
     */
    public static WebDriver create(Browser browser) {
        // デフォルトブラウザとしてIEを設定
        if (browser == null) {
            browser = Browser.IE;
        }

        WebDriver driver = null;

        // WebDriverの生成
        switch (browser) {
        case IE:
            logger.debug("create driver : {}", InternetExplorerDriver.class.getName());
            System.setProperty(InternetExplorerDriverService.IE_DRIVER_EXE_PROPERTY, prop.getString("driver.url.ie"));
            // TODO 必要？
            DesiredCapabilities capability = DesiredCapabilities.internetExplorer();
            capability.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
            driver = new InternetExplorerDriver();
            break;
        case CHROME:
            logger.debug("create driver : {}", ChromeDriver.class.getName());
            System.setProperty(ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY, prop.getString("driver.url.chrome"));
            driver = new ChromeDriver();
            break;
        case FIREFOX:
            logger.debug("create driver : {}", FirefoxDriver.class.getName());
            driver = new FirefoxDriver();
            break;
        }

        return driver;
    }
}
