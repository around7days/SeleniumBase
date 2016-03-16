package selenium.com;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * SeleniumTest抽象クラス
 * @author 7days
 */
public abstract class AbstractSeleniumTest extends AbstractSelenium {

    /** ロガー */
    private static final Logger logger = LoggerFactory.getLogger(AbstractSeleniumTest.class);

    /** Capture */
    protected static SeleniumCapture capture;

    /** テスト実施結果 */
    protected static boolean isError = false;

    @BeforeClass
    public static void init() {
        logger.debug("処理開始 --------------------------------------------------------------------------------------");
        logger.debug("open web browser : {}", browser.name());
        /* Webブラウザの起動 */
        driver = WebDriverFactory.create(browser);
        /* Captureの生成 */
        capture = new SeleniumCapture(driver);
    }

    @Before
    public void before() throws Exception {
        logger.debug("テストケース開始 ------------------------------------------------------------------------------");
        isError = false;
    }

    @After
    public void after() throws Exception {
        if (isError) {
            // エラー発生時にキャプチャを取得
            logger.error("異常終了");
            capture.screenShotError();
        }
        logger.debug("テストケース終了 ------------------------------------------------------------------------------");
    }

    @AfterClass
    public static void finish() {
        logger.debug("close web browser : {}", browser.name());
        /* Webブラウザの終了 */
        driver.quit();
        logger.debug("処理終了 --------------------------------------------------------------------------------------");
    }
}
