package selenium.base;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import selenium.base.WebDriverFactory.Browser;

/**
 * SeleniumTest抽象クラス
 * @author 7days
 */
public abstract class AbstractSeleniumTest {

    /** ロガー */
    private static final Logger logger = LoggerFactory.getLogger(AbstractSeleniumTest.class);

    /** プロパティ */
    private static final SeleniumPropertyManager prop = SeleniumPropertyManager.INSTANCE;

    /** 実行ブラウザ */
    private static final Browser browser = Browser.getEnum(prop.getString("execute.browser"));

    /** WebDriver */
    protected static WebDriver driver = null;

    /** BaseWindowHandle */
    protected static String baseWindowHandle = null;

    /** Capture */
    protected static SeleniumCapture capture;

    /** Helper */
    protected static SeleniumHelper helper;

    /** テスト名 */
    @Rule
    public TestName testName = new TestName();

    @BeforeClass
    public static void init() throws Exception {
        logger.info("処理開始 ---------------------------------------------------------------------------------------");
        logger.debug("open web browser -> {}", browser.name());
        /* Webブラウザの起動 */
        driver = WebDriverFactory.create(browser);
        baseWindowHandle = driver.getWindowHandle();
        /* Captureの生成 */
        capture = new SeleniumCapture(driver);
        /* Helperの生成 */
        helper = new SeleniumHelper(driver);
    }

    @Before
    public void before() throws Exception {
        logger.debug("テストケース開始 ------------------------------------------------------------------------------");
        String classNm = getSimpleNameContainEnclosingClass(this.getClass());
        String methodNm = testName.getMethodName();
        capture.setPrefix(classNm + "#" + methodNm);
    }

    @After
    public void after() throws Exception {
        /* 初期化 */
        driver.get("about:blank");
        capture.setPrefix("");
        driver.manage().deleteAllCookies();
        logger.debug("テストケース終了 ------------------------------------------------------------------------------");
    }

    @AfterClass
    public static void finish() throws Exception {
        logger.debug("close web browser -> {}", browser.name());
        /* Webブラウザの終了 */
        driver.quit();
        logger.info("処理終了 ---------------------------------------------------------------------------------------");
    }

    /**
     * トップレベルクラス名を含むシンプルクラス名の取得<br>
     * 例）org.test.selenium.UserRegistTest → UserRegistTest<br>
     * 例）org.test.selenium.UserRegistTest$新規登録テストグループ → UserRegistTest$新規登録テストグループ
     * @param cls
     * @return
     */
    private String getSimpleNameContainEnclosingClass(Class<?> cls) {
        String classNm = cls.getName();
        int idx = classNm.lastIndexOf(".");
        if (idx != -1) {
            return classNm.substring(idx + 1, classNm.length());
        } else {
            return classNm;
        }
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
