package selenium.base;

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

    /** 待機（秒） */
    private static int waitTime = 2 * 1000;

    @BeforeClass
    public static void init() throws Exception {
        logger.info("処理開始 ---------------------------------------------------------------------------------------");
        logger.debug("open web browser -> {}", browser.name());
        /* Webブラウザの起動 */
        driver = WebDriverFactory.create(browser);
        baseWindowHandle = driver.getWindowHandle();
        /* Captureの生成 */
        capture = new SeleniumCapture(driver);
    }

    @Before
    public void before() throws Exception {
        logger.debug("テストケース開始 ------------------------------------------------------------------------------");
    }

    @After
    public void after() throws Exception {
        logger.debug("テストケース終了 ------------------------------------------------------------------------------");
    }

    @AfterClass
    public static void finish() throws Exception {
        logger.debug("close web browser -> {}", browser.name());
        Thread.sleep(waitTime);
        /* Webブラウザの終了 */
        driver.quit();
        logger.info("処理終了 ---------------------------------------------------------------------------------------");
    }

    // // 失敗したときはキャプチャを取得
    // @Override
    // protected void failed(Throwable e,
    // Description description) {
    // logger.error("system error", e);
    // try {
    // capture.screenShotError();
    // } catch (IOException ioe) {
    // // エラー時はログ出力のみ行って握りつぶす
    // logger.warn("capture error", ioe);
    // }
    // super.failed(e, description);
    // }
}
