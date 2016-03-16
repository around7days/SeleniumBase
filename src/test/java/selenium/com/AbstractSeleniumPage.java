package selenium.com;

import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * SeleniumPage抽象クラス
 * @author 7days
 */
public abstract class AbstractSeleniumPage extends AbstractSelenium {

    /** ロガー */
    @SuppressWarnings("unused")
    private static final Logger logger = LoggerFactory.getLogger(AbstractSeleniumPage.class);

    /**
     * PageFactoryを使用してWebElementをマッピングする
     * @return Page
     */
    @SuppressWarnings("unchecked")
    public <T extends AbstractSeleniumPage> T initialize() {
        return (T) PageFactory.initElements(driver, this.getClass());
    }

    /**
     * タイトルを取得する。
     * @return title
     */
    public String getTitle() {
        return driver.getTitle();
    }
}
