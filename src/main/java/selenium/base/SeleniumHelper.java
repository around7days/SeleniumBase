package selenium.base;

import java.util.List;
import java.util.Set;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.TargetLocator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Selenium操作のヘルパークラス
 * @author 7days
 */
public class SeleniumHelper {

    /** プロパティ */
    protected static final SeleniumPropertyManager prop = SeleniumPropertyManager.INSTANCE;

    /** WebDriver */
    private WebDriver driver;

    /** BaseWindowHandle */
    private String baseWindowHandle;

    /**
     * コンストラクタ
     * @param driver
     */
    SeleniumHelper(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * コンストラクタ
     * @param driver
     * @param baseWindowHandle
     */
    SeleniumHelper(WebDriver driver, String baseWindowHandle) {
        this.driver = driver;
        this.baseWindowHandle = baseWindowHandle;
    }

    /**
     * URLアクセス
     * @param url
     */
    public void url(String url) {
        driver.get(url);
    }

    /**
     * タイトル取得
     * @return title
     */
    public String getTitle() {
        return driver.getTitle();
    }

    /**
     * エレメント取得
     * @param by
     * @return WebElement
     */
    public WebElement $(By by) {
        return driver.findElement(by);
    }

    /**
     * エレメント取得
     * @param by
     * @return WebElement List
     */
    public List<WebElement> $$(By by) {
        return driver.findElements(by);
    }

    /**
     * エレメント取得(selectBox)
     * @param by
     * @return Select
     */
    public Select $select(By by) {
        return new Select(driver.findElement(by));
    }

    /**
     * エレメント取得(alert)
     * @return Alert
     */
    public Alert $switchToAlert() {
        return driver.switchTo().alert();
    }

    /**
     * switchオブジェクトの取得
     * @return TargetLocator
     */
    public TargetLocator $switch() {
        return driver.switchTo();
    }

    /**
     * Frame一覧の取得
     * @return WebElement List
     */
    public List<WebElement> $$frame() {
        return $$(By.tagName("frame"));
    }

    /**
     * Frame変更
     * @param index
     * @return WebDriver
     */
    public WebDriver $switchToFrame(int index) {
        return driver.switchTo().frame(index);
    }

    /**
     * Frame変更
     * @param nameOrId
     * @return WebDriver
     */
    public WebDriver $switchToFrame(String nameOrId) {
        return driver.switchTo().frame(nameOrId);
    }

    /**
     * Frame変更
     * @param frameElement
     * @return WebDriver
     */
    public WebDriver $switchToFrame(WebElement frameElement) {
        return driver.switchTo().frame(frameElement);
    }

    /**
     * Frame変更
     * @param frameElement
     * @return WebDriver
     */
    public WebDriver $switchToParentFrame() {
        return driver.switchTo().parentFrame();
    }

    /**
     * WebDriverWaitの生成<br>
     * （明示的な待機）
     * @return WebDriverWait
     */
    public WebDriverWait $wait() {
        String waitDefault = prop.getString("wait.explicit.default");
        return $wait(Integer.valueOf(waitDefault));
    }

    /**
     * WebDriverWaitの生成<br>
     * （明示的な待機）
     * @param seconds 待機秒
     * @return WebDriverWait
     */
    public WebDriverWait $wait(int seconds) {
        return new WebDriverWait(driver, seconds);
    }

    /**
     * WindowHandleを切り替える（簡易版）<br>
     *
     * <pre>
     * 現在のWindowHandle以外に切り替える仕様のため、
     * ３つ以上WindowHandleが存在すると正しく動作しない可能性がある
     * </pre>
     */
    public void $switchToWindowHandle() {
        // 現在のハンドルを取得
        String currentHandle = driver.getWindowHandle();

        // 現在開いているウィンドウのハンドルを取得
        Set<String> windowList = driver.getWindowHandles();
        for (String window : windowList) {
            if (!currentHandle.equals(window)) {
                // 親画面以外のhandleなら切替
                driver.switchTo().window(window);
            }
        }
    }

    /**
     * WindowHandleをベースに戻す<br>
     */
    public void $switchToBaseWindowHandle() {
        driver.switchTo().window(baseWindowHandle);
    }

    /**
     * valueの値を取得
     * @param element
     * @return
     */
    public String getValue(WebElement element) {
        return element.getAttribute("value");
    }

    /**
     * 選択されているセレクトボックスの値（テキスト）を取得
     * @param element
     * @return
     */
    public String getSelectedText(WebElement element) {
        return new Select(element).getFirstSelectedOption().getText();
    }

    /**
     * 値を初期化してから設定する
     * @param element
     * @param val
     */
    public void sendKeys(WebElement element,
                         Object obj) {
        element.clear();
        element.sendKeys(obj.toString());
    }

    /**
     * セレクトボックスを選択（index指定）
     * @param element
     * @param index
     */
    public void selectByIndex(WebElement element,
                              int index) {
        new Select(element).selectByIndex(index);
    }

    /**
     * セレクトボックスを選択（value指定）
     * @param element
     * @param value
     */
    public void selectByValue(WebElement element,
                              String value) {
        new Select(element).selectByValue(value);
    }

    /**
     * セレクトボックスを選択（text指定）
     * @param element
     * @param text
     */
    public void selectByText(WebElement element,
                             String text) {
        new Select(element).selectByVisibleText(text);
    }

    /**
     * 要素の存在チェックを行う<br>
     * （要素にアクセスしてエラーが出るかどうかで確認）
     * @param element
     * @return true：存在する false：存在しない
     */
    public boolean exists(WebElement element) {
        try {
            element.getText();
        } catch (NoSuchElementException e) {
            // エラー自体は握りつぶす
            return false;
        }
        return true;
    }

}
