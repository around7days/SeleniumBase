package selenium.sample.page;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import selenium.com.AbstractSeleniumPage;

/**
 * ログインページ
 * @author 7days
 */
public class LoginPage extends AbstractSeleniumPage {

    @FindBy(name = "userid")
    @CacheLookup
    /** ログインID */
    private WebElement userId;

    @FindBy(name = "password")
    @CacheLookup
    /** パスワード */
    private WebElement password;

    /** ログインボタン */
    @FindBy(name = "btnname")
    @CacheLookup
    private WebElement loginBtn;

    /**
     * PageFactoryを使用してWebElementをマッピングする
     * @return
     */
    public LoginPage initialize() {
        // googleのトップページへ
        driver.get("http://pisetmdbdv01.mew.co.jp/pb_demo/ActionServlet");

        return PageFactory.initElements(driver, this.getClass());
    }

    /**
     * ログインIDを設定します。
     * @param userId ログインID
     */
    public void setUserId(String userId) {
        this.userId.sendKeys(userId);
    }

    /**
     * パスワードを設定します。
     * @param password パスワード
     */
    public void setPassword(String password) {
        this.password.sendKeys(password);
    }

    /**
     * ログインを行う
     * @return MenuPage
     */
    public MenuPage loginBtnClick() {
        this.loginBtn.click();

        return new MenuPage().initialize();
    }

}
