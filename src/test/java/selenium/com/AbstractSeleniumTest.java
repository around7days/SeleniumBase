package selenium.com;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import selenium.com.WebDriverFactory.Browser;

/**
 * SeleniumTest抽象クラス
 * @author 7days
 */
public abstract class AbstractSeleniumTest {

    /** ロガー */
    @SuppressWarnings("unused")
    private static final Logger logger = LoggerFactory.getLogger(AbstractSeleniumTest.class);

    /** プロパティ */
    protected static SeleniumPropertyManager prop = SeleniumPropertyManager.INSTANCE;

    /** WebDriver */
    protected static WebDriver driver = null;

    /** 実行ブラウザリスト */
    protected static List<Browser> browserList = new ArrayList<Browser>() {
        {
            /** 実行ブラウザリストの取得 */
            if (Boolean.valueOf(prop.getString("execute.browser.ie"))) add(Browser.IE);
            if (Boolean.valueOf(prop.getString("execute.browser.chrome"))) add(Browser.CHROME);
            if (Boolean.valueOf(prop.getString("execute.browser.firefox"))) add(Browser.FIREFOX);
        }
    };

    /**
     * Sleep処理
     * @param second 秒
     * @throws InterruptedException
     */
    protected void sleep(int second) throws InterruptedException {
        Thread.sleep(second * 1000);
    }
}
