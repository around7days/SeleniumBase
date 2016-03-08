package selenium.com;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import selenium.com.WebDriverFactory.Browser;

/**
 * WebDriverManagerクラス<br>
 * （シングルトン）
 * @author 7days
 */
public enum WebDriverManager {
    INSTANCE;

    /** Logger */
    @SuppressWarnings("unused")
    private static final Logger logger = LoggerFactory.getLogger(WebDriverManager.class);

    /** プロパティ */
    private static final SeleniumPropertyManager prop = SeleniumPropertyManager.INSTANCE;

    /** 実行ブラウザ */
    private static final Browser browser = Browser.getEnum(prop.getString("execute.browser"));

    /** WebDriver */
    private static final WebDriver driver = WebDriverFactory.create(browser);

    /**
     * WebDriverの取得
     * @return driver
     */
    public WebDriver getDriver() {
        return driver;
    }
}
