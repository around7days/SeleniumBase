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

    @BeforeClass
    public static void init() {
        logger.debug("-------------------- 処理開始 --------------------");
        logger.debug("open web browser : {}", browser.name());
        /* Webブラウザの起動 */
        driver = WebDriverFactory.create(browser);
    }

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    @AfterClass
    public static void finish() {
        logger.debug("close web browser : {}", browser.name());
        /* Webブラウザの終了 */
        driver.quit();
        logger.debug("-------------------- 処理終了 --------------------");
    }
}
