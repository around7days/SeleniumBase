package selenium.base;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * WebDriverFactoryクラス
 * @author 7days
 */
public class WebDriverFactory {
    /** Logger */
    private static final Logger logger = LoggerFactory.getLogger(WebDriverFactory.class);

    /** プロパティ */
    protected static final SeleniumPropertyManager prop = SeleniumPropertyManager.INSTANCE;

    /**
     * Webブラウザタイプ
     */
    public static enum Browser {
        IE, CHROME, FIREFOX;

        public static Browser getEnum(String str) {
            for (Browser browser : Browser.values()) {
                if (browser.name().toLowerCase().equals(str.toLowerCase())) {
                    return browser;
                }
            }
            return null;
        }
    }

    /**
     * WebDriverの生成
     * @param browser Webブラウザタイプ
     * @return WebDriver
     */
    public static WebDriver create(Browser browser) {

        WebDriver driver = null;

        /*
         * WebDriverの生成
         */
        switch (browser) {
        case IE:
            logger.debug("create driver -> {}", InternetExplorerDriver.class.getName());
            // ドライバー設定
            System.setProperty(InternetExplorerDriverService.IE_DRIVER_EXE_PROPERTY, prop.getString("driver.url.ie"));

            // オプション設定
            DesiredCapabilities capability = DesiredCapabilities.internetExplorer();
            // 保護モードチェックエラースルー
            capability.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);

            // 生成
            driver = new InternetExplorerDriver(capability);
            break;

        case CHROME:
            logger.debug("create driver -> {}", ChromeDriver.class.getName());
            // ドライバー設定
            System.setProperty(ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY, prop.getString("driver.url.chrome"));

            // オプション設定
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--disable-extensions"); // 拡張機能無効化

            // 生成
            driver = new ChromeDriver(options);
            break;

        case FIREFOX:
            logger.debug("create driver -> {}", FirefoxDriver.class.getName());
            // ドライバー設定

            // オプション設定
            FirefoxProfile profile = new FirefoxProfile();

            // 生成
            driver = new FirefoxDriver(profile);
            break;
        }

        /*
         * ディスプレイ位置
         */
        {
            String x = prop.getString("display.position.x");
            String y = prop.getString("display.position.y");
            if (!x.isEmpty() && !y.isEmpty()) {
                driver.manage().window().setPosition(new Point(Integer.valueOf(x), Integer.valueOf(y)));
            }
        }

        /*
         * ディスプレイサイズ
         */
        {
            String maximize = prop.getString("display.size.maximize");
            String width = prop.getString("display.size.width");
            String height = prop.getString("display.size.height");
            if (Boolean.valueOf(maximize)) {
                // 最大化
                driver.manage().window().maximize();
            } else {
                // サイズ指定
                if (!width.isEmpty() && !height.isEmpty()) {
                    driver.manage().window().setSize(new Dimension(Integer.valueOf(width), Integer.valueOf(height)));
                }
            }
        }

        /*
         * 暗黙的な待機(秒)の設定
         */
        {
            String waitSecond = prop.getString("wait.implicit");
            if (!waitSecond.isEmpty()) {
                driver.manage().timeouts().implicitlyWait(Integer.valueOf(waitSecond), TimeUnit.SECONDS);
            }
        }

        return driver;
    }
}
