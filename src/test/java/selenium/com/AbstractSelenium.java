package selenium.com;

import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.TargetLocator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import selenium.com.WebDriverFactory.Browser;

/**
 * Selenium抽象クラス
 * @author 7days
 */
public abstract class AbstractSelenium {

    /** ロガー */
    @SuppressWarnings("unused")
    private static final Logger logger = LoggerFactory.getLogger(AbstractSelenium.class);

    /** プロパティ */
    protected static final SeleniumPropertyManager prop = SeleniumPropertyManager.INSTANCE;

    /** 実行ブラウザ */
    protected static final Browser browser = Browser.getEnum(prop.getString("execute.browser"));

    /** WebDriver */
    protected static WebDriver driver = null;

    /**
     * URLアクセス
     * @param url
     */
    protected void $url(String url) {
        driver.get(url);
    }

    /**
     * エレメント取得
     * @param by
     * @return WebElement
     */
    protected WebElement $(By by) {
        return driver.findElement(by);
    }

    /**
     * エレメント取得
     * @param by
     * @return WebElement List
     */
    protected List<WebElement> $$(By by) {
        return driver.findElements(by);
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
    protected Alert $switchToAlert() {
        return driver.switchTo().alert();
    }

    /**
     * switchオブジェクトの取得
     * @return TargetLocator
     */
    protected TargetLocator $switch() {
        return driver.switchTo();
    }

    /**
     * Frame一覧の取得
     * @return WebElement List
     */
    protected List<WebElement> $$getFrameElements() {
        return $$(By.tagName("frame"));
    }

    /**
     * Frame変更
     * @param index
     * @return WebDriver
     */
    protected WebDriver $switchToFrame(int index) {
        return driver.switchTo().frame(index);
    }

    /**
     * Frame変更
     * @param nameOrId
     * @return WebDriver
     */
    protected WebDriver $switchToFrame(String nameOrId) {
        return driver.switchTo().frame(nameOrId);
    }

    /**
     * Frame変更
     * @param frameElement
     * @return WebDriver
     */
    protected WebDriver $switchToFrame(WebElement frameElement) {
        return driver.switchTo().frame(frameElement);
    }

    /**
     * Frame変更
     * @param frameElement
     * @return WebDriver
     */
    protected WebDriver $switchToParentFrame() {
        return driver.switchTo().parentFrame();
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
}
