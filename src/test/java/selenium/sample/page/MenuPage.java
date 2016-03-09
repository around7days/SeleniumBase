package selenium.sample.page;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import selenium.com.AbstractSeleniumPage;

/**
 * メニューページ
 * @author 7days
 */
public class MenuPage extends AbstractSeleniumPage {

    @FindBy(partialLinkText = "マニフェスト予約・登録")
    @CacheLookup
    private WebElement manifestYoyakuTorok;

    /**
     * PageFactoryを使用してWebElementをマッピングする
     * @return
     */
    public MenuPage initialize() {
        return PageFactory.initElements(driver, this.getClass());
    }
}
