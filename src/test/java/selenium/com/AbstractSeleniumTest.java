package selenium.com;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * SeleniumTest抽象クラス
 * @author 7days
 */
public abstract class AbstractSeleniumTest {

    /** ロガー */
    private static final Logger logger = LoggerFactory.getLogger(AbstractSeleniumTest.class);

    /** プロパティ */
    protected static final SeleniumPropertyManager prop = SeleniumPropertyManager.INSTANCE;

    /** WebDriver */
    protected static final WebDriver driver = WebDriverManager.INSTANCE.getDriver();

    @BeforeClass
    public static void init() {
        logger.info("---------- 処理開始(class) ----------");
    }

    @Before
    public void before() throws Exception {
        logger.info("---------- 処理開始(test) ----------");
    }

    @After
    public void after() throws Exception {
        logger.info("---------- 処理終了(test) ----------");
    }

    @AfterClass
    public static void finish() {
        logger.info("---------- 処理終了(class) ----------");
        /* Webブラウザの終了 */
        driver.quit();
    }

    /**
     * Sleep処理
     * @param second 秒
     * @throws InterruptedException
     */
    protected void sleep(int second) throws InterruptedException {
        Thread.sleep(second * 1000);
    }
}
