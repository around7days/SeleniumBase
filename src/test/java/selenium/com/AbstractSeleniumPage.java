package selenium.com;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * SeleniumPage抽象クラス
 * @author 7days
 */
public abstract class AbstractSeleniumPage {

    /** ロガー */
    @SuppressWarnings("unused")
    private static final Logger logger = LoggerFactory.getLogger(AbstractSeleniumPage.class);

    /** プロパティ */
    protected static SeleniumPropertyManager prop = SeleniumPropertyManager.INSTANCE;

    /** WebDriver */
    protected WebDriver driver = null;

    /**
     * エレメント取得
     * @param by
     * @return WebElement
     */
    protected WebElement $(By by) {
        return driver.findElement(by);
    }

    /**
     * エレメント取得(button)<br>
     * input type="button" value="文字列"
     * @param value 文字列
     * @return WebElement
     */
    protected WebElement $buttonVal(String value) {
        return driver.findElement(By.cssSelector("input[type='button'][value='" + value + "']"));
    }

    /**
     * エレメント取得(selectBox)
     * @param by
     * @return Select
     */
    protected Select $select(By by) {
        return new Select(driver.findElement(by));
    }

    /**
     * エレメント取得(alert)
     * @return Alert
     */
    protected Alert $alert() {
        return driver.switchTo().alert();
    }

    /**
     * WebDriverWaitの生成<br>
     * （明示的な待機）
     * @return WebDriverWait
     */
    protected WebDriverWait $wait() {
        String waitDefault = prop.getString("wait.explicit.default");
        return $wait(Integer.valueOf(waitDefault));
    }

    /**
     * WebDriverWaitの生成<br>
     * （明示的な待機）
     * @param seconds 待機秒
     * @return WebDriverWait
     */
    protected WebDriverWait $wait(int seconds) {
        return new WebDriverWait(driver, seconds);
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
